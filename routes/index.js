let express = require('express');
let router = express.Router();
/* GET home page. */

router.get('/', (req, res) => {
  console.log('post request from ', req.headers['x-forwarded-for'] || req.connection.remoteAddress)
  res.render('index.ejs', { title: "express" });
})


router.post('/', function (req, res, next) {
  console.log('post request from ', req.headers['x-forwarded-for'] || req.connection.remoteAddress)

  const strPublicKey = req.body.publicKey
  const strPlainText = req.body.plainText
  const strSignature = req.body.signature;

  var exec = require('child_process').exec, child;
  child = exec(`java -jar "C:\\Users\\IOUIOU\\Desktop\\univ\\2021-1\\보안\\과제\\4차과제\\RSA-in-node.js\\routes\\RSADecryption.jar" ${strPublicKey} ${strPlainText} ${strSignature}`,
    function (error, stdout, stderr) {
      console.log('검증 결과: ' + stdout);
      if (error !== null) {
        console.log('exec error: ' + error);
      }
    });


  res.send('verify');
});

module.exports = router;
