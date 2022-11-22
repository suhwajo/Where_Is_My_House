var express = require("express");
var app = express();
var client_id = "YOUR_CLIENT_ID";
var client_secret = "YOUR_CLIENT_SECRET";
app.get("/search/blog", function (req, res) {
  var api_url =
    "https://openapi.naver.com/v1/search/blog?query=" +
    encodeURI(req.query.query); // JSON 결과
  //   var api_url = 'https://openapi.naver.com/v1/search/blog.xml?query=' + encodeURI(req.query.query); // XML 결과
  var request = require("request");
  var options = {
    url: api_url,
    headers: {
      "X-Naver-Client-Id": "rFr9TtwJYXE5sHUdRA6Q",
      "X-Naver-Client-Secret": "IFbfdROEaY",
    },
  };
  request.get(options, function (error, response, body) {
    if (!error && response.statusCode == 200) {
      res.writeHead(200, { "Content-Type": "text/json;charset=utf-8" });
      res.end(body);
    } else {
      res.status(response.statusCode).end();
      console.log("error = " + response.statusCode);
    }
  });
});
app.listen(8080, function () {
  console.log(
    "http://localhost:8080/search/blog?query=검색어 app listening on port 3000!"
  );
});
