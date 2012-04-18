package org.grails.plugins.bootstrap.file.upload

import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException

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
     * @attr params map containing parameters that will be sent to the controller on upload (defaults to [:])
     * @attr allowDelete boolean indicating whether delete is allowed (defaults to true)
     */
    def fileUpload = { attrs, body ->
        def id = attrs.id ?: 'fileupload'
        def maxFileSize = attrs.maxFileSize ?: 5000000
        def acceptFileTypes = attrs.acceptFileTypes ?: "/(\\.|\\/)(gif|jpe?g|png)\$/i"
        def resizeMaxWidth = attrs.resizeMaxWidth ?: 1920
        def resizeMaxHeight = attrs.resizeMaxHeight ?: 1200
        def params = attrs.params ?: [:]
        def allowDelete

        if (attrs.allowDelete == null || attrs.allowDelete == ''){
            allowDelete = true
        } else {
            allowDelete = attrs.allowDelete
        }

        //println("allow delete? " + attrs.allowDelete + "/" + allowDelete)

        out << """
        <form id="${id}" action="${createLink(controller: attrs.controller, action: attrs.action)}" method="POST" enctype="multipart/form-data">"""

        params.each { key, value ->
            out << hiddenField(name: key, value: value)
        }

        out << """
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
            """

        if (allowDelete) {
            out << """
                <button type="button" class="btn btn-danger delete">
                  <i class="icon-trash icon-white"></i>
                <span>${message(code:'fileupload.delete', default:'Delete')}</span>
                </button>
                """
        }

        out << """
            <input id="${id}-toggle" type="checkbox" class="toggle">&nbsp;<label style="display:inline" for="${id}-toggle">${message(code: 'fileupload.select.all', default: 'Select all')}</label>
            </div>
            <div class="span2">
                <!-- The global progress bar -->
                <div class="progress progress-success progress-striped active fade">
                  <div class="bar" style="width:0%;"></div>
                </div>
            </div>
            </div>
        <!-- The loading indicator is shown during image processing -->
        <div class="fileupload-loading"></div>
            <br>"""

        try {
            out << render(template: "/bootstrapFileUpload/list")
        } catch (GrailsTagException exc) {
            out << render(template: "/bootstrapFileUpload/list", plugin: "bootstrap-file-upload")
        }

        out << """
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

                    \$('#${id}').each(function () {
                        var that = this;
                        \$.getJSON(this.action, ${params ? '{' + params.collect{k,v->k+':'+v}.join(',') + '}' : '{}'} ,function (result) {
                            if (result && result.length) {
                                \$(that).fileupload('option', 'done').call(that, null, {result: result});
                            }
                        });
                    });
                 });
            """
        }

        out << """
<!-- The template to display files available for upload -->
<script id="template-upload" type="text/x-tmpl">"""

        try {
            out << render(template: "/bootstrapFileUpload/upload")
        } catch (GrailsTagException exc) {
            out << render(template: "/bootstrapFileUpload/upload", plugin: "bootstrap-file-upload")
        }

        out << """
</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">"""

        try {
            out << render(template: "/bootstrapFileUpload/download", model: [allowDelete: allowDelete])
        } catch (GrailsTagException exc) {
            out << render(template: "/bootstrapFileUpload/download", model: [allowDelete: allowDelete], plugin: "bootstrap-file-upload")
        }

        out << """
</script>
        """
    }

    def imageGallery = { attrs, body ->
        try {
            out << render(template: "/bootstrapFileUpload/gallery")
        } catch (GrailsTagException exc) {
            out << render(template: "/bootstrapFileUpload/gallery", plugin: "bootstrap-file-upload")
        }
    }

}
