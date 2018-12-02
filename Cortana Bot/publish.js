var zipFolder = require('zip-folder');
var path = require('path');
var fs = require('fs');
var request = require('request');

var rootFolder = path.resolve('.');
var zipPath = path.resolve(rootFolder, '../tripplanner-b905.zip');
var kuduApi = 'https://tripplanner-b905.scm.azurewebsites.net/api/zip/site/wwwroot';
var userName = '$tripplanner-b905';
var password = '09xA8z58cqjvfxybhxD81rWNwoJSELXoCXzi2NpsoYNMGrJ6xl2STkgki4xR';

function uploadZip(callback) {
  fs.createReadStream(zipPath).pipe(request.put(kuduApi, {
    auth: {
      username: userName,
      password: password,
      sendImmediately: true
    },
    headers: {
      "Content-Type": "applicaton/zip"
    }
  }))
  .on('response', function(resp){
    if (resp.statusCode >= 200 && resp.statusCode < 300) {
      fs.unlink(zipPath);
      callback(null);
    } else if (resp.statusCode >= 400) {
      callback(resp);
    }
  })
  .on('error', function(err) {
    callback(err)
  });
}

function publish(callback) {
  zipFolder(rootFolder, zipPath, function(err) {
    if (!err) {
      uploadZip(callback);
    } else {
      callback(err);
    }
  })
}

publish(function(err) {
  if (!err) {
    console.log('tripplanner-b905 publish');
  } else {
    console.error('failed to publish tripplanner-b905', err);
  }
});