var express = require('express');

var app = express();
app.use(express.static(__dirname + '/'));
app.use(express.static(__dirname + '/bstweb'));

app.use(function(req, res){
    //console.log(req);
    res.sendFile(__dirname + '/bstweb/index.html');
}); 

var server = app.listen(process.env.PORT || 8080, function () {
  var port = server.address().port;
  console.log('App is listening at port : %s', port);
});