package org.grails.plugins.bootstrap.file.upload

import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.http.HttpMethod
import javax.naming.directory.InvalidAttributeValueException

class BootstrapFileUploadTagLib {

    static namespace = "bsfu"

    /**
     * Displays the form to upload multiple files
     *
     * @attr id the id of the form (defaults to fileupload)
     * @attr controller REQUIRED the controller to send files to
     * @attr action REQUIRED the action to send files to
     *
     * @attr type The HTTP request method for the file uploads. Can be "POST" or "PUT" (defaults to "POST").
     * @attr dataType The type of data that is expected back from the server (defaults to 'json')
     * @attr namespace The namespace used for event handler binding on the dropZone and fileInput collections.
     * If not set, the name of the widget ("fileupload") is used.
     * @attr dropZone Selector of the element that serves as a drop zone in browsers that support drag and drop (defaults to null)
     * @attr fileInput Selector of the element that is listened for change events.
     * If undefined, it is set to the file input fields inside of the widget element on plugin initialization.
     * @attr paramName The parameter name for the file form data (the request argument name).
     * If undefined or empty, the name property of the file input field is used, or "files[]" if the file input name property is also empty.
     * Can be a string or an array of strings.
     * @attr singleFileUploads By default, each file of a selection is uploaded using an individual request for XHR type uploads.
     * Set this option to false to upload file selections in one request each (defaults to true)
     * @attr limitMultiFileUploads To limit the number of files uploaded with one XHR request,
     * set the following option to an integer greater than 0. This option is ignored, if singleFileUploads is set to true (defaults to undefined)
     * @attr sequentialUploads Set this option to true to issue all file upload requests in a sequential order instead of
     * simultaneous requests (defaults to false)
     * @attr limitConcurrentUploads To limit the number of concurrent uploads, set this option to an integer value greater than 0 (defaults to undefined)
     * @attr forceIframeTransport Set this option to true to force iframe transport uploads, even if the browser is capable of XHR file uploads.
     * This can be useful for cross-site file uploads, if the Access-Control-Allow-Origin header cannot be set for the server-side
     * upload handler which is required for cross-site XHR file uploads. Defaults to false.
     * @attr redirect Set this option to the location of a redirect url on the origin server (the server that hosts the file upload form),
     * for cross-domain iframe transport uploads. If set, this value is sent as part of the form data to the upload server.
     * The upload server is supposed to redirect the browser to this url after the upload completes and append the upload information
     * as URL-encoded JSON string to the redirect URL, e.g. by replacing the "%s" character sequence.
     * @attr redirectParamName The parameter name for the redirect url, sent as part of the form data and set to 'redirect'
     * if this option is empty.
     * @attr postMessage Set this option to the location of a postMessage window on the upload server, to enable cross-domain postMessage
     * transport uploads. This feature is currently only fully supported by Google Chrome.
     * @attr multipart By default, XHR file uploads are sent as multipart/form-data. The iframe transport is always using multipart/form-data.
     * If this option is set to false, the file content is streamed to the server url instead of sending a RFC 2388 multipart message
     * for XMLHttpRequest file uploads. Non-multipart uploads are also referred to as HTTP PUT file upload. Additional form data is ignored
     * when the multipart option is set to false. Non-multipart uploads (multipart: false) are broken in Safari 5.1. Defaults to true.
     * @attr maxChunkSize To upload large files in smaller chunks, set this option to a preferred maximum chunk size.
     * If set to 0, null or undefined, or the browser does not support the required Blob API, files will be uploaded as a whole.
     * For chunked uploads to work in Mozilla Firefox, the multipart option has to be set to false.
     * This is due to Gecko 2.0 (Firefox 4 etc.) adding blobs with an empty filename when building a multipart upload
     * request using the FormData interface - see Bugzilla entry #649150. Several server-side frameworks (including PHP and Django)
     * cannot handle multipart file uploads with empty filenames. If this option is enabled and singleFileUploads is set to false,
     * only the first file of a selection will be uploaded. Defaults to undefined.
     * @attr recalculateProgress By default, failed (abort or error) file uploads are removed from the global progress calculation.
     * Set this option to false to prevent recalculating the global progress data. Defaults to true.
     * @attr formData Additional form data to be sent along with the file uploads can be set using this option, which accepts an array
     * of objects with name and value properties, a function returning such an array, a FormData object (for XHR file uploads),
     * or a simple object. The form of the first fileInput is given as parameter to the function.
     * Additional form data is ignored when the multipart option is set to false. Defaults to a function returning the form fields
     * as serialized Array.
     * @attr process A list of file processing actions. Defaults to [].
     * @attr autoUpload By default, files added to the UI widget are uploaded as soon as the user clicks on the start buttons.
     * To enable automatic uploads, set this option to true. Defaults to false.
     * @attr maxNumberOfFiles This option limits the number of files that are allowed to be uploaded using this widget.
     * By default, unlimited file uploads are allowed.
     * @attr maxFileSize Maximum file size (defaults to 5000000, ie 5MB). This option has only an effect for browsers supporting the File API.
     * @attr minFileSize The minimum allowed file size, by default 1 byte. The minimum allowed file size, by default 1 byte.
     * @attr acceptFileTypes regular expression for accepted file type (defaults to /(\.|\/)(gif|jpe?g|png)$/i)
     * @attr previewSourceFileTypes The regular expression to define for which files a preview image is shown, matched against the file type.
     * Preview images (before upload) are only displayed for browsers supporting the URL or webkitURL APIs or the readAsDataURL
     * method of the FileReader interface. Default: /^image\/(gif|jpeg|png)$/
     * @attr previewMaxSourceFileSize The maximum file size for preview images in bytes. Defaults to 5000000.
     * @attr previewMaxWidth The maximum width of the preview images. Defaults to 80.
     * @attr previewMaxHeight The maximum height of the preview images. Defaults to 80.
     * @attr previewAsCanvas By default, preview images are displayed as canvas elements if supported by the browser.
     * Set this option to false to always display preview images as img elements. Defaults to true.
     * @attr filesContainer Selector of the container for the files listed for upload / download. Is transformed into a jQuery object
     * if set as DOM node or string. Defaults to '.files'
     * @attr prependFiles By default, files are appended to the files container. Set this option to true, to prepend files instead.
     * Defaults to false.
     * @attr uploadTemplateId The ID of the upload template, given as parameter to the tmpl() method to set the uploadTemplate option.
     * Defaults to 'template-upload'.
     * @attr downloadTemplateId The ID of the download template, given as parameter to the tmpl() method to set the downloadTemplate option.
     * Defaults to 'template-upload'
     * @attr loadImages Boolean to indicate whether images should be loaded when the page is first loaded or not. Setting this option to false
     * allows you to control when and how this loading happens. Defaults to true.
     */
    def fileUpload = { attrs, body ->
        String id = attrs.id ?: 'fileupload'
        String url = createLink(controller: attrs.controller, action: attrs.action)

        HttpMethod type = attrs.type ? HttpMethod.valueOf(attrs.type) : HttpMethod.POST
        String dataType = attrs.dataType ?: 'json'
        String namespace = attrs.namespace ?: id
        String dropZone = attrs.dropZone ?: null
        String fileInput = attrs.fileInput ?: null
        boolean replaceFileInput = attrs.fileInput == true ||
                attrs.fileInput == 'true' ||
                attrs.fileInput == '' ||
                attrs.fileInput == null ?: false
        String paramName = attrs.paramName ?: 'files[]'
        boolean singleFileUploads = attrs.singleFileUploads == true ||
                attrs.singleFileUploads == 'true' ||
                attrs.singleFileUploads == '' ||
                attrs.singleFileUploads == null ?: false
        Integer limitMultiFileUploads = attrs.limitMultiFileUploads ? Integer.parseInt(attrs.limitMultiFileUploads) : null
        boolean sequentialUploads = attrs.sequentialUploads == 'true' || attrs.sequentialUploads == true ?: false
        Integer limitConcurrentUploads = attrs.limitConcurrentUploads ? Integer.parseInt(attrs.limitConcurrentUploads) : null
        boolean forceIframeTransport = attrs.forceIframeTransport == 'true' || attrs.forceIframeTransport == true ?: false
        String redirect = attrs.redirect ?: null
        String redirectParamName = attrs.redirectParamName ?: null
        String postMessage = attrs.postMessage ?: null
        boolean multipart = attrs.multipart == 'true' || attrs.multipart == true || attrs.multipart == '' || attrs.multipart == null ?: false
        long maxChunkSize = attrs.maxChunkSize ?: 0L
        boolean recalculateProgress = attrs.recalculateProgress == 'true' || attrs.recalculateProgress == true || attrs.recalculateProgress == '' || attrs.recalculateProgress == null ?: false
        int progressInterval = attrs.progressInterval ?: 100
        int bitrateInterval = attrs.bitrateInterval ?: 500
        Map formData = attrs.formData ?: (attrs.params ?: null)
        String process = attrs.process ?: null
        boolean autoUpload = attrs.autoUpload == 'true' || attrs.autoUpload == true ?: false
        Integer maxNumberOfFiles = attrs.maxNumberOfFiles ?: null
        Integer maxFileSize = attrs.maxFileSize ?: null
        Integer minFileSize = attrs.minFileSize ?: 0
        String acceptFileTypes = attrs.acceptFileTypes ?: "/(\\.|\\/)(gif|jpe?g|png)\$/i"
        String previewSourceFileTypes = attrs.previewSourceFileTypes ?: "/^image\\/(gif|jpeg|png)\$/"
        long previewMaxSourceFileSize = attrs.previewMaxSourceFileSize ?: 5000000
        int previewMaxWidth = attrs.previewMaxWidth ?: 80
        int previewMaxHeight = attrs.previewMaxHeight ?: 80
        boolean previewAsCanvas = attrs.previewAsCanvas == true || attrs.previewAsCanvas == 'true' || attrs.previewAsCanvas == '' || attrs.previewAsCanvas == null ?: false
        String filesContainer = attrs.filesContainer ?: '.files'
        boolean prependFiles = attrs.prependFiles == 'true' || attrs.prependFiles == true ?: false
        String uploadTemplateId = attrs.uploadTemplateId ?: 'template-upload'
        String downloadTemplateId = attrs.downloadTemplateId ?: 'template-download'
        boolean loadImages = attrs.loadImages == '' || attrs.loadImages == 'true' || attrs.loadImages == true || attrs.loadImages == null

        if(!singleFileUploads && !multipart){
            throw new InvalidAttributeValueException("Uploading multiple files with one request requires the multipart option to be set to true (the default).")
        }

        def renderedList
        try {
            renderedList = render(template: "/bootstrapFileUpload/list", model: [filesContainer: filesContainer]).toString()
        } catch (GrailsTagException exc) {
            renderedList = render(template: "/bootstrapFileUpload/list", model: [filesContainer: filesContainer], plugin: 'bootstrap-file-upload').toString()
        }

        def model = [
                id: id,
                url: url,
                type: type,
                multipart: multipart,
                formData: formData,
                paramName: paramName,
                list: renderedList,
                filesContainer: filesContainer,
                autoUpload: autoUpload
        ]
        try {
            out << render(template: "/bootstrapFileUpload/form", model: model)
        } catch(GrailsTagException exc) {
            out << render(template: "/bootstrapFileUpload/form", plugin: "bootstrap-file-upload", model: model)
        }

        out << r.script(null) {
            out << """
                \$(function(){
                    \$('#${id}').fileupload({
                        url: '${url}',
                        ${type ? "type: '${type}'," : ""}
                        ${dataType ? "dataType: '${dataType}'," : ""}
                        ${namespace ? "namespace: '${namespace}'," : ""}
                        ${dropZone ? "dropZone: \$('${dropZone}')," : ""}
                        ${fileInput ? "fileInput: \$('${fileInput}')," : ""}
                        replaceFileInput: ${replaceFileInput},
                        ${paramName ? "paramName: '${paramName}'," : ""}
                        singleFileUploads: ${singleFileUploads},
                        ${limitMultiFileUploads ? "limitMultiFileUploads: ${limitMultiFileUploads}," : ""}
                        sequentialUploads: ${sequentialUploads},
                        ${limitConcurrentUploads ? "limitConcurrentUploads: ${limitConcurrentUploads}," : ""}
                        forceIframeTransport: ${forceIframeTransport},
                        ${redirect ? "redirect: '${redirect}'," : ""}
                        ${redirectParamName ? "redirectParamName: '${redirectParamName}'," : ""}
                        ${postMessage ? "postMessage: '${postMessage}'," : ""}
                        multipart: ${multipart},
                        ${maxChunkSize ? "maxChunkSize: ${maxChunkSize}," : ""}
                        recalculateProgress: ${recalculateProgress},
                        progressInterval: ${progressInterval},
                        bitrateInterval: ${bitrateInterval},
                        ${formData ? "formData: [${formData.collect{entry->'{name:\'' + entry.key + '\',value:\'' + entry.value + '\'}'}.join(',')}]," : ""}
                        ${process ? "process: ${process}," : ""}
                        autoUpload: ${autoUpload},
                        ${maxNumberOfFiles ? "maxNumberOfFiles: ${maxNumberOfFiles}," : ""}
                        ${maxFileSize ? "maxFileSize: ${maxFileSize}," : ""}
                        ${minFileSize ? "minFileSize: ${minFileSize}," : ""}
                        ${acceptFileTypes ? "acceptFileTypes: ${acceptFileTypes}," : ""}
                        ${previewSourceFileTypes ? "previewSourceFileTypes: ${previewSourceFileTypes}," : ""}
                        ${previewMaxSourceFileSize ? "previewMaxSourceFileSize: ${previewMaxSourceFileSize}," : ""}
                        ${previewMaxWidth ? "previewMaxWidth: ${previewMaxWidth}," : ""}
                        ${previewMaxHeight ? "previewMaxHeight: ${previewMaxHeight}," : ""}
                        previewAsCanvas: ${previewAsCanvas},
                        filesContainer: \$('${filesContainer}'),
                        prependFiles: ${prependFiles},
                        uploadTemplateId: '${uploadTemplateId}',
                        downloadTemplateId: '${downloadTemplateId}'
                    });"""

            if(loadImages){
                out << """
                    \$('#${id}').each(function () {
                        var that = this;
                        \$.getJSON(this.action, ${formData ? '{' + formData.collect{k,v->k+':'+'\''+v+'\''}.join(',') + '}' : '{}'}, function (result) {
                            if (result && result.length) {
                                \$(that).fileupload('option', 'done').call(that, null, {result: result});
                            }
                        });
                    });"""
            }
            out << """
                 });
            """
        }

        out << """
<!-- The template to display files available for upload -->
<script id="template-upload" type="text/x-tmpl">"""

        try {
            out << render(template: "/bootstrapFileUpload/upload", model: [autoUpload: autoUpload])
        } catch (GrailsTagException exc) {
            out << render(template: "/bootstrapFileUpload/upload", plugin: "bootstrap-file-upload", model: [autoUpload: autoUpload])
        }

        out << """
</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">"""

        try {
            out << render(template: "/bootstrapFileUpload/download")
        } catch (GrailsTagException exc) {
            out << render(template: "/bootstrapFileUpload/download", plugin: "bootstrap-file-upload")
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
