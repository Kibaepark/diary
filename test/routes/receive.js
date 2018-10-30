var express = require('express');
var router = express.Router();
var fs = require('fs');
/* GET home page. */
router.get('/', function(req, res, next) {
  console.log(req.query);
  var json = require('../babo/'+req.query.id+'.json');
  var txt = JSON.stringify(json);
  res.send(txt);
});
module.exports = router;