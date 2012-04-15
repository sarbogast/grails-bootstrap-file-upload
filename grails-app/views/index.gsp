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
  <!-- The file upload form used as target for the file upload widget -->
  <form id="fileupload" action="image/upload/" method="POST" enctype="multipart/form-data">
    <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
    <div class="row fileupload-buttonbar">
      <div class="span7">
        <!-- The fileinput-button span is used to style the file input field as button -->
        <span class="btn btn-success fileinput-button">
          <i class="icon-plus icon-white"></i>
          <span>Add files...</span>
          <input type="file" name="files[]" multiple>
        </span>
        <button type="submit" class="btn btn-primary start">
          <i class="icon-upload icon-white"></i>
          <span>Start upload</span>
        </button>
        <button type="reset" class="btn btn-warning cancel">
          <i class="icon-ban-circle icon-white"></i>
          <span>Cancel upload</span>
        </button>
        <button type="button" class="btn btn-danger delete">
          <i class="icon-trash icon-white"></i>
          <span>Delete</span>
        </button>
        <input type="checkbox" class="toggle">
      </div>
      <div class="span5">
        <!-- The global progress bar -->
        <div class="progress progress-success progress-striped active fade">
          <div class="bar" style="width:0%;"></div>
        </div>
      </div>
    </div>
    <!-- The loading indicator is shown during image processing -->
    <div class="fileupload-loading"></div>
    <br>
    <!-- The table listing the files available for upload/download -->
    <table class="table table-striped"><tbody class="files" data-toggle="modal-gallery" data-target="#modal-gallery"></tbody></table>
  </form>
</div>
<!-- modal-gallery is the modal dialog used for the image gallery -->
<div id="modal-gallery" class="modal modal-gallery hide fade">
  <div class="modal-header">
    <a class="close" data-dismiss="modal">&times;</a>
    <h3 class="modal-title"></h3>
  </div>
  <div class="modal-body"><div class="modal-image"></div></div>
  <div class="modal-footer">
    <a class="btn modal-download" target="_blank">
      <i class="icon-download"></i>
      <span>Download</span>
    </a>
    <a class="btn btn-success modal-play modal-slideshow" data-slideshow="5000">
      <i class="icon-play icon-white"></i>
      <span>Slideshow</span>
    </a>
    <a class="btn btn-info modal-prev">
      <i class="icon-arrow-left icon-white"></i>
      <span>Previous</span>
    </a>
    <a class="btn btn-primary modal-next">
      <span>Next</span>
      <i class="icon-arrow-right icon-white"></i>
    </a>
  </div>
</div>

<r:layoutResources/>
<!-- The template to display files available for upload -->
<script id="template-upload" type="text/x-tmpl">
  {% for (var i=0, file; file=o.files[i]; i++) { %}
  <tr class="template-upload fade">
    <td class="preview"><span class="fade"></span></td>
    <td class="name"><span>{%=file.name%}</span></td>
    <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
    {% if (file.error) { %}
    <td class="error" colspan="2"><span class="label label-important">{%=locale.fileupload.error%}</span> {%=locale.fileupload.errors[file.error] || file.error%}</td>
    {% } else if (o.files.valid && !i) { %}
    <td>
      <div class="progress progress-success progress-striped active"><div class="bar" style="width:0%;"></div></div>
    </td>
    <td class="start">{% if (!o.options.autoUpload) { %}
      <button class="btn btn-primary">
        <i class="icon-upload icon-white"></i>
        <span>{%=locale.fileupload.start%}</span>
      </button>
      {% } %}</td>
    {% } else { %}
    <td colspan="2"></td>
    {% } %}
    <td class="cancel">{% if (!i) { %}
      <button class="btn btn-warning">
        <i class="icon-ban-circle icon-white"></i>
        <span>{%=locale.fileupload.cancel%}</span>
      </button>
      {% } %}</td>
  </tr>
  {% } %}
</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
  {% for (var i=0, file; file=o.files[i]; i++) { %}
  <tr class="template-download fade">
    {% if (file.error) { %}
    <td></td>
    <td class="name"><span>{%=file.name%}</span></td>
    <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
    <td class="error" colspan="2"><span class="label label-important">{%=locale.fileupload.error%}</span> {%=locale.fileupload.errors[file.error] || file.error%}</td>
    {% } else { %}
    <td class="preview">{% if (file.thumbnail_url) { %}
      <a href="{%=file.url%}" title="{%=file.name%}" rel="gallery" download="{%=file.name%}"><img src="{%=file.thumbnail_url%}"></a>
      {% } %}</td>
    <td class="name">
      <a href="{%=file.url%}" title="{%=file.name%}" rel="{%=file.thumbnail_url&&'gallery'%}" download="{%=file.name%}">{%=file.name%}</a>
    </td>
    <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
    <td colspan="2"></td>
    {% } %}
    <td class="delete">
      <button class="btn btn-danger" data-type="{%=file.delete_type%}" data-url="{%=file.delete_url%}">
        <i class="icon-trash icon-white"></i>
        <span>{%=locale.fileupload.destroy%}</span>
      </button>
      <input type="checkbox" name="delete" value="1">
    </td>
  </tr>
  {% } %}
</script>
</body>
</html>
