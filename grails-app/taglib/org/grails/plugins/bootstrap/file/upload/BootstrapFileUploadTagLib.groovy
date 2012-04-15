package org.grails.plugins.bootstrap.file.upload

class BootstrapFileUploadTagLib {

    static namespace = "bsfu"

    /**
     * Displays the form to upload multiple files
     *
     * @attr id the id of the form (defaults to fileupload)
     * @attr controller REQUIRED the controller to send files to
     * @attr action REQUIRED the action to send files to
     * @attr maxFileSize Maximum file size (defaults to 5000000, ie 5MB)
     * @attr acceptFileTypes regular expression for accepted file type (defaults to /(\.|\/)(gif|jpe?g|png)$/i)
     * @attr resizeMaxWidth maximum width images will be resized to in supporting browsers (defaults to 1920)
     * @attr resizeMaxHeight maximum height images will be resized to in supporting browsers (defaults to 1200)
     */
    def fileUpload = { attrs, body ->
        def id = attrs.id ?: 'fileupload'
        def maxFileSize = attrs.maxFileSize ?: 5000000
        def acceptFileTypes = attrs.acceptFileTypes ?: "/(\\.|\\/)(gif|jpe?g|png)\$/i"
        def resizeMaxWidth = attrs.resizeMaxWidth ?: 1920
        def resizeMaxHeight = attrs.resizeMaxHeight ?: 1200

        out << """
        <form id="${id}" action="${createLink(controller: attrs.controller, action: attrs.action)}" method="POST" enctype="multipart/form-data">
            <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
            <div class="row fileupload-buttonbar">
            <div class="span7">
            <!-- The fileinput-button span is used to style the file input field as button -->
            <span class="btn btn-success fileinput-button">
            <i class="icon-plus icon-white"></i>
              <span>${message(code: 'fileupload.add.files', default:'Add files...')}</span>
            <input type="file" name="files[]" multiple>
            </span>
            <button type="submit" class="btn btn-primary start">
              <i class="icon-upload icon-white"></i>
            <span>${message(code:'fileupload.start.upload', default: 'Start upload')}</span>
            </button>
            <button type="reset" class="btn btn-warning cancel">
            <i class="icon-ban-circle icon-white"></i>
              <span>${message(code:'fileupload.cancel.upload', default: 'Cancel upload')}</span>
            </button>
            <button type="button" class="btn btn-danger delete">
              <i class="icon-trash icon-white"></i>
            <span>${message(code:'fileupload.delete', default:'Delete')}</span>
            </button>
            <input id="${id}-toggle" type="checkbox" class="toggle">
            <label for="${id}-toggle">${message(code: 'fileupload.select.all', default: 'Select all')}</label>
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
        """

        out << r.script(null) {
            out << """
                \$(function(){
                    \$('#${id}').fileupload();

                    \$('#${id}').fileupload('option', {
                        url: '${createLink(controller: attrs.controller, action: attrs.action)}',
                        maxFileSize: ${maxFileSize},
                        acceptFileTypes: ${acceptFileTypes},
                        resizeMaxWidth: ${resizeMaxWidth},
                        resizeMaxHeight: ${resizeMaxHeight}
                     });
                 });
            """
        }

        out << """
<!-- The template to display files available for upload -->
<script id="template-upload" type="text/x-tmpl">
  {% for (var i=0, file; file=o.files[i]; i++) { %}
  <tr class="template-upload fade">
    <td class="preview"><span class="fade"></span></td>
    <td class="name"><span>{%=file.name%}</span></td>
    <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
    {% if (file.error) { %}
        <td class="error" colspan="2"><span class="label label-important">${message(code:'fileupload.error')}</span>
        {% if (file.error == 'maxFileSize') { %}
        ${message(code: 'fileupload.errors.maxFileSize')}
        {% } else if(file.error == 'minFileSize') { %}
        ${message(code: 'fileupload.errors.minFileSize')}
        {% } else if(file.error == 'acceptFileTypes') { %}
        ${message(code: 'fileupload.errors.acceptFileTypes')}
        {% } else if(file.error == 'maxNumberOfFiles') { %}
        ${message(code: 'fileupload.errors.maxNumberOfFiles')}
        {% } else if(file.error == 'uploadedBytes') { %}
        ${message(code: 'fileupload.errors.uploadedBytes')}
        {% } else if(file.error == 'emptyResult') { %}
        ${message(code: 'fileupload.errors.emptyResult')}
        {% } else { file.error } %}
        </td>
    {% } else if (o.files.valid && !i) { %}
    <td>
      <div class="progress progress-success progress-striped active"><div class="bar" style="width:0%;"></div></div>
    </td>
    <td class="start">{% if (!o.options.autoUpload) { %}
      <button class="btn btn-primary">
        <i class="icon-upload icon-white"></i>
        <span>${message(code:'fileupload.start')}</span>
      </button>
      {% } %}</td>
    {% } else { %}
    <td colspan="2"></td>
    {% } %}
    <td class="cancel">{% if (!i) { %}
      <button class="btn btn-warning">
        <i class="icon-ban-circle icon-white"></i>
        <span>${message(code:'fileupload.cancel')}</span>
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
    <td class="error" colspan="2"><span class="label label-important">${message(code:'fileupload.error')}</span>
        {% if (file.error == 'maxFileSize') { %}
        ${message(code: 'fileupload.errors.maxFileSize')}
        {% } else if(file.error == 'minFileSize') { %}
        ${message(code: 'fileupload.errors.minFileSize')}
        {% } else if(file.error == 'acceptFileTypes') { %}
        ${message(code: 'fileupload.errors.acceptFileTypes')}
        {% } else if(file.error == 'maxNumberOfFiles') { %}
        ${message(code: 'fileupload.errors.maxNumberOfFiles')}
        {% } else if(file.error == 'uploadedBytes') { %}
        ${message(code: 'fileupload.errors.uploadedBytes')}
        {% } else if(file.error == 'emptyResult') { %}
        ${message(code: 'fileupload.errors.emptyResult')}
        {% } else { file.error } %}
    </td>
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
        <span>${message(code:'fileupload.destroy')}</span>
      </button>
      <input type="checkbox" name="delete" value="1">
    </td>
  </tr>
  {% } %}
</script>
        """
    }

    def imageGallery = { attrs, body ->
        out << """
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
        """
    }

}
