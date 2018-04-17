package com.bst.service.app;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.bst.service.constants.Constants;
import com.bst.service.controller.Controller;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

/**
 * BST Service HTTP VertBox.
 * 
 * @author asifaham
 */
@Slf4j
public class BSTServiceHttpVertbox extends AbstractVerticle {
    
    /**
     *  Router.
     */
    private Router router;
    
    /**
     * Server Host name.
     */
    private String host;
    
    /**
     * Server Port.
     */
    private Integer port;
    
    /**
     * Base path of rest services.
     */
    private String basePath;
    
    /**
     * Constructor.
     */
    public BSTServiceHttpVertbox() {
        this.vertx = Vertx.vertx();
        this.init();
    }
    
    /**
     * Initializes Vert Box.
     */
    private void init(){
        log.info("Initializing BST Service HTTP Vertbox ...");
        this.router = Router.router(this.vertx);
        Properties properties = new Properties();
        try {
            @Cleanup
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("env.properties");
            properties.load(in);
            this.host = properties.getProperty("host");
            this.port = Integer.parseInt(properties.getProperty("port"));
            this.basePath = properties.getProperty("basePath");
        } catch (IOException ex) {
            log.warn("Failed to initialize http vertbox..", ex);
        } 
    }

    public static void main(String[] args) {
        // Running standalone
        log.info("Starting BST Service HTTP Vertbox ...");
        BSTServiceHttpVertbox webservice = new BSTServiceHttpVertbox();
        webservice.startService();
    }
    
    /**
     * Method to deploy vertbox on vertx.
     */
    private void startService() {
        VertxOptions options = new VertxOptions().setWorkerPoolSize(Constants.workerPoolSize);
        Runner.runVerticle(new BSTServiceHttpVertbox(), options);
        log.info("Running " + this.getClass().getName() + " at http://" + 
                this.host + ":" + this.port + this.basePath);
    }
    
    /**
     * Start method of Verticle.
     * 
     * @param future 
     */
    @Override
    public void start(Future<Void> future) {
        initRouter();

        HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(router::accept).listen(port, host, result -> {
            if (result.succeeded()) {
                future.complete();
            } else {
                future.fail(result.cause());
            }
        });
    }
    
    /**
     * Setup router.
     */
    private void initRouter() {
        this.router.route().handler(CookieHandler.create());
        this.router.route().handler(BodyHandler.create());
        this.router.route().handler(CorsHandler.create("http://localhost:8080")
                                        .allowedMethods(new HashSet<>(Arrays.asList(HttpMethod.POST
                                                , HttpMethod.GET
                                                , HttpMethod.DELETE
                                                , HttpMethod.PUT
                                                , HttpMethod.OPTIONS)))
                                        .allowedHeader("Content-Type, Authorization")
                                        .allowCredentials(true));
        SessionHandler sessionHandler = SessionHandler.create(LocalSessionStore.create(this.vertx));
        sessionHandler.setCookieHttpOnlyFlag(true);
        sessionHandler.setNagHttps(false);
        sessionHandler.setSessionCookieName(Constants.sessionName);
        sessionHandler.setSessionTimeout(Integer.MAX_VALUE);
        this.router.route().handler(sessionHandler);
        
        this.router.post(basePath + "/add/node").blockingHandler(new Controller(this.vertx)::addNode, true);
        this.router.delete(basePath + "/delete/node/:" + Constants.key).blockingHandler(new Controller(this.vertx)::removeNode, true);
        this.router.post(basePath + "/find/node").blockingHandler(new Controller(this.vertx)::getPosition, true);
        this.router.get(basePath + "/view").blockingHandler(new Controller(this.vertx)::printTree, true);
        this.router.get(basePath + "/stats").blockingHandler(new Controller(this.vertx)::getStats, true);
           
    } 
}
