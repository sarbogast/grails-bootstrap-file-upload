<!DOCTYPE HTML>
<!--
/*
 * jQuery File Upload Plugin Demo 6.5.2
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2010, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/MIT
 */
-->
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>jQuery File Upload Demo</title>
  <meta name="description" content="File Upload widget with multiple file selection, drag&amp;drop support, progress bar and preview images for jQuery. Supports cross-domain, chunked and resumable file uploads. Works with any server-side platform (Google App Engine, PHP, Python, Ruby on Rails, Java, etc.) that supports standard HTML form file uploads.">
  <meta name="viewport" content="width=device-width">

  <r:require modules="bootstrap-file-upload, bootstrap-image-gallery"/>

  <!-- Shim to make HTML5 elements usable in older Internet Explorer versions -->
  <!--[if lt IE 9]><script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->

  <r:layoutResources/>
</head>
<body style="padding-top: 60px;">
<div class="navbar navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container">
      <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </a>
      <a class="brand" href="https://github.com/blueimp/jQuery-File-Upload">jQuery File Upload</a>
      <div class="nav-collapse">
        <ul class="nav">
          <li class="active"><a href="#">Demo</a></li>
          <li><a href="https://github.com/blueimp/jQuery-File-Upload/downloads">Downloads</a></li>
          <li><a href="https://github.com/blueimp/jQuery-File-Upload">Source Code</a></li>
          <li><a href="https://github.com/blueimp/jQuery-File-Upload/wiki">Documentation</a></li>
          <li><a href="https://github.com/blueimp/jQuery-File-Upload/issues">Issues</a></li>
          <li><a href="test/">Test</a></li>
          <li><a href="https://blueimp.net">&copy; Sebastian Tschan</a></li>
        </ul>
      </div>
    </div>
  </div>
</div>
<div class="container">
  <div class="page-header">
    <h1>jQuery File Upload Demo</h1>
  </div>
  <bsfu:fileUpload controller="image" action="upload"/>
</div>
<bsfu:imageGallery/>
<r:layoutResources/>
</body>
</html>
