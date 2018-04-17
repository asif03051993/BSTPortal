package com.bst.service.app;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * Runner to deploy verticels on Vertx.
 * 
 * @author asifaham
 */
public class Runner {

    private static final String WEB_JAVA_DIR = "/src/main/java/";

    public static void runClusteredVerticle(Class clazz) {
        Runner.runVerticle(WEB_JAVA_DIR, clazz, new VertxOptions().setClustered(true), null);
    }

    public static void runVerticle(Class clazz) {
        Runner.runVerticle(WEB_JAVA_DIR, clazz, new VertxOptions().setClustered(false), null);
    }

    public static void runVerticle(Verticle verticle) {
        Runner.runVerticle(WEB_JAVA_DIR, verticle, new VertxOptions().setClustered(false), null);
    }
    
    public static void runVerticle(Verticle verticle, VertxOptions options) {
        Runner.runVerticle(WEB_JAVA_DIR, verticle, options, null);
    }
    
    public static void runVerticle(Verticle verticle, VertxOptions options, DeploymentOptions deploymentOptions) {
        Runner.runVerticle(WEB_JAVA_DIR, verticle, options, deploymentOptions);
    }

    public static void runVerticle(Class clazz, DeploymentOptions options) {
        Runner.runVerticle(WEB_JAVA_DIR, clazz, new VertxOptions().setClustered(false), options);
    }

    public static void runVerticle(String appDir, Class clazz, VertxOptions options, DeploymentOptions deploymentOptions) {
        Runner.runVerticle(appDir + clazz.getPackage().getName().replace(".", "/"), clazz.getName(), options, deploymentOptions);
    }

    public static void runScriptVerticle(String prefix, String scriptName, VertxOptions options) {
        File file = new File(scriptName);
        String dirPart = file.getParent();
        String scriptDir = prefix + dirPart;
        Runner.runVerticle(scriptDir, scriptDir + "/" + file.getName(), options, null);
    }

    public static void runVerticle(String appDir, String verticleID, VertxOptions options, DeploymentOptions deploymentOptions) {
        if (options == null) {
            // Default parameter
            options = new VertxOptions();
        }
    // Smart cwd detection

    // Based on the current directory (.) and the desired directory (appDir), we try to compute the vertx.cwd
        // directory:
        try {
            // We need to use the canonical file. Without the file name is .
            File current = new File(".").getCanonicalFile();
            if (appDir.startsWith(current.getName()) && !appDir.equals(current.getName())) {
                appDir = appDir.substring(current.getName().length() + 1);
            }
        } catch (IOException e) {
            // Ignore it.
        }

        System.setProperty("vertx.cwd", appDir);
        Consumer<Vertx> runner = vertx -> {
            try {
                if (deploymentOptions != null) {
                    vertx.deployVerticle(verticleID, deploymentOptions);
                } else {
                    vertx.deployVerticle(verticleID);
                }
            } catch (Throwable t) {
            }
        };
        if (options.isClustered()) {
            Vertx.clusteredVertx(options, res -> {
                if (res.succeeded()) {
                    Vertx vertx = res.result();
                    runner.accept(vertx);
                } else {
                }
            });
        } else {
            Vertx vertx = Vertx.vertx(options);
            runner.accept(vertx);
        }
    }
    
    public static void runVerticle(String appDir, Verticle verticle, VertxOptions options, DeploymentOptions deploymentOptions) {
        if (options == null) {
            // Default parameter
            options = new VertxOptions();
        }
    // Smart cwd detection

    // Based on the current directory (.) and the desired directory (appDir), we try to compute the vertx.cwd
        // directory:
        try {
            // We need to use the canonical file. Without the file name is .
            File current = new File(".").getCanonicalFile();
            if (appDir.startsWith(current.getName()) && !appDir.equals(current.getName())) {
                appDir = appDir.substring(current.getName().length() + 1);
            }
        } catch (IOException e) {
            // Ignore it.
        }

        System.setProperty("vertx.cwd", appDir);
        Consumer<Vertx> runner = vertx -> {
            try {
                if (deploymentOptions != null) {
                    vertx.deployVerticle(verticle, deploymentOptions);
                } else {
                    vertx.deployVerticle(verticle);
                }
            } catch (Throwable t) {
            }
        };
        if (options.isClustered()) {
            Vertx.clusteredVertx(options, res -> {
                if (res.succeeded()) {
                    Vertx vertx = res.result();
                    runner.accept(vertx);
                } else {
                }
            });
        } else {
            Vertx vertx = Vertx.vertx(options);
            runner.accept(vertx);
        }
    }
}
