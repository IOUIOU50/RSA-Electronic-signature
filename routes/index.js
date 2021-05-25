var express = require('express');
var router = express.Router();



/* GET home page. */

router.get('/', (req, res) => {
  console.log('post request from ', req.headers['x-forwarded-for'] || req.connection.remoteAddress)
  res.render('index.ejs', {title : "express"});
})


router.post('/', function(req, res, next) {
  console.log();
  console.log();
  console.log();
  console.log();
  console.log();
  console.log();
  console.log(req.body);
  console.log();
  console.log();
  console.log();
  console.log();
  console.log();
  console.log('post request from ', req.headers['x-forwarded-for'] || req.connection.remoteAddress)
  res.send('hello world');
});



module.exports = router;
