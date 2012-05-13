Description
===========
This plugin integrates the excellent Sebastian Tschan's jquery-file-upload (https://github.com/blueimp/jQuery-File-Upload)
into grails 2.0. To view a nice demo of Sebastian's work, [http://blueimp.github.com/jQuery-File-Upload/](click here).

It integrates nicely with Twitter Bootstrap and is completely internationalizable.

Version 1.2.0 of this plugin integrates commit e98f2ae9bfda1dbe2a2e651bec765757237ff104 of jQuery-file-upload

Configuration
=============

This plugin doesn't require any configuration.

Integration
===========

First, add the following line in the header of your page: `<r:require modules="bootstrap-file-upload"/>`.
This will import all of the CSS and Javascript resources needed by this plugin.

Then, add the following tag to your page: `<bsfu:fileUpload action="upload" controller="image"/>`

`controller` and `action` are the only 2 required attributes. Other attributes include:

* id: the id of the form (defaults to fileupload)
* controller: REQUIRED the controller to send files to
* action: REQUIRED the action to send files to
* type: The HTTP request method for the file uploads. Can be "POST" or "PUT" (defaults to "POST").
* dataType: The type of data that is expected back from the server (defaults to 'json')
* namespace: The namespace used for event handler binding on the dropZone and fileInput collections. If not set, the name of the widget ("fileupload") is used.
* dropZone: Selector of the element that serves as a drop zone in browsers that support drag and drop (defaults to null)
* fileInput: Selector of the element that is listened for change events. If undefined, it is set to the file input fields inside of the widget element on plugin initialization.
* paramName: The parameter name for the file form data (the request argument name). If undefined or empty, the name property of the file input field is used, or "files[]" if the file input name property is also empty. Can be a string or an array of strings.
* singleFileUploads: By default, each file of a selection is uploaded using an individual request for XHR type uploads. Set this option to false to upload file selections in one request each (defaults to true)
* limitMultiFileUploads: To limit the number of files uploaded with one XHR request, set the following option to an integer greater than 0. This option is ignored, if singleFileUploads is set to true (defaults to undefined)
* sequentialUploads: Set this option to true to issue all file upload requests in a sequential order instead of simultaneous requests (defaults to false)
* limitConcurrentUploads: To limit the number of concurrent uploads, set this option to an integer value greater than 0 (defaults to undefined)
* forceIframeTransport: Set this option to true to force iframe transport uploads, even if the browser is capable of XHR file uploads. This can be useful for cross-site file uploads, if the Access-Control-Allow-Origin header cannot be set for the server-side upload handler which is required for cross-site XHR file uploads. Defaults to false.
* redirect: Set this option to the location of a redirect url on the origin server (the server that hosts the file upload form), for cross-domain iframe transport uploads. If set, this value is sent as part of the form data to the upload server. The upload server is supposed to redirect the browser to this url after the upload completes and append the upload information as URL-encoded JSON string to the redirect URL, e.g. by replacing the "%s" character sequence.
* redirectParamName: The parameter name for the redirect url, sent as part of the form data and set to 'redirect' if this option is empty.
* postMessage: Set this option to the location of a postMessage window on the upload server, to enable cross-domain postMessage transport uploads. This feature is currently only fully supported by Google Chrome.
* multipart: By default, XHR file uploads are sent as multipart/form-data. The iframe transport is always using multipart/form-data. If this option is set to false, the file content is streamed to the server url instead of sending a RFC 2388 multipart message for XMLHttpRequest file uploads. Non-multipart uploads are also referred to as HTTP PUT file upload. Additional form data is ignored when the multipart option is set to false. Non-multipart uploads (multipart: false) are broken in Safari 5.1. Defaults to true.
* maxChunkSize: To upload large files in smaller chunks, set this option to a preferred maximum chunk size. If set to 0, null or undefined, or the browser does not support the required Blob API, files will be uploaded as a whole. For chunked uploads to work in Mozilla Firefox, the multipart option has to be set to false. This is due to Gecko 2.0 (Firefox 4 etc.) adding blobs with an empty filename when building a multipart upload request using the FormData interface - see Bugzilla entry #649150. Several server-side frameworks (including PHP and Django) cannot handle multipart file uploads with empty filenames. If this option is enabled and singleFileUploads is set to false, only the first file of a selection will be uploaded. Defaults to undefined.
* recalculateProgress: By default, failed (abort or error) file uploads are removed from the global progress calculation. Set this option to false to prevent recalculating the global progress data. Defaults to true.
* formData: Additional form data to be sent along with the file uploads can be set using this option, which accepts an array of objects with name and value properties, a function returning such an array, a FormData object (for XHR file uploads), or a simple object. The form of the first fileInput is given as parameter to the function. Additional form data is ignored when the multipart option is set to false. Defaults to a function returning the form fields as serialized Array.
* process: A list of file processing actions. Defaults to [].
* autoUpload: By default, files added to the UI widget are uploaded as soon as the user clicks on the start buttons. To enable automatic uploads, set this option to true. Defaults to false.
* maxNumberOfFiles: This option limits the number of files that are allowed to be uploaded using this widget. By default, unlimited file uploads are allowed.
* maxFileSize: Maximum file size (defaults to 5000000, ie 5MB). This option has only an effect for browsers supporting the File API.
* minFileSize: The minimum allowed file size, by default 1 byte. The minimum allowed file size, by default 1 byte.
* acceptFileTypes: regular expression for accepted file type (defaults to /(\.|\/)(gif|jpe?g|png)$/i)
* previewSourceFileTypes: The regular expression to define for which files a preview image is shown, matched against the file type. Preview images (before upload) are only displayed for browsers supporting the URL or webkitURL APIs or the readAsDataURL method of the FileReader interface. Default: /^image\/(gif|jpeg|png)$/
* previewMaxSourceFileSize: The maximum file size for preview images in bytes. Defaults to 5000000.
* previewMaxWidth: The maximum width of the preview images. Defaults to 80.
* previewMaxHeight: The maximum height of the preview images. Defaults to 80.
* previewAsCanvas: By default, preview images are displayed as canvas elements if supported by the browser. Set this option to false to always display preview images as img elements. Defaults to true.
* filesContainer: Selector of the container for the files listed for upload / download. Is transformed into a jQuery object if set as DOM node or string. Defaults to '.files'
* prependFiles: By default, files are appended to the files container. Set this option to true, to prepend files instead. Defaults to false.
* uploadTemplateId: The ID of the upload template, given as parameter to the tmpl() method to set the uploadTemplate option. Defaults to 'template-upload'.
* downloadTemplateId: The ID of the download template, given as parameter to the tmpl() method to set the downloadTemplate option. Defaults to 'template-upload'.
* loadImages Boolean to indicate whether images should be loaded when the page is first loaded or not. Setting this option to false allows you to control when and how this loading happens. Defaults to true.

Note that starting with version 2.0.0 of the plugin, the list of attributes for the taglib matches jquery file upload options
as close as possible. allowDelete and buttonBarClass have been removed. dropZone is now a jQuery selector and params has been renamed as
formData.

File handling
=============

Once your page is in place, it is important to understand that the javascript code will GET your controller action once
to load existing files, if any, and then it will POST to your controller action once for each file you upload.

Here is an example implementation of the controller:

```groovy
import org.springframework.http.HttpStatus
import grails.converters.JSON
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import org.imgscalr.Scalr
import javax.imageio.ImageIO

class ImageController {
    def upload() {
        switch(request.method){
            case "GET":
                def results = []
                Image.findAll().each { Image picture ->
                    results << [
                            name: picture.originalFilename,
                            size: picture.fileSize,
                            url: createLink(controller:'image', action:'picture', id: picture.id),
                            thumbnail_url: createLink(controller:'image', action:'thumbnail', id: picture.id),
                            delete_url: createLink(controller:'image', action:'delete', id: picture.id),
                            delete_type: "DELETE"
                    ]
                }
                render results as JSON
                break;
            case "POST":
                def results = []
                if (request instanceof MultipartHttpServletRequest){
                    for(filename in request.getFileNames()){
                        MultipartFile file = request.getFile(filename)

                        String newFilenameBase = UUID.randomUUID().toString()
                        String originalFileExtension = file.originalFilename.substring(file.originalFilename.lastIndexOf("."))
                        String newFilename = newFilenameBase + originalFileExtension
                        String storageDirectory = grailsApplication.config.file.upload.directory?:'/tmp'

                        File newFile = new File("$storageDirectory/$newFilename")
                        file.transferTo(newFile)

                        BufferedImage thumbnail = Scalr.resize(ImageIO.read(newFile), 290);
                        String thumbnailFilename = newFilenameBase + '-thumbnail.png'
                        File thumbnailFile = new File("$storageDirectory/$thumbnailFilename")
                        ImageIO.write(thumbnail, 'png', thumbnailFile)

                        Image picture = new Image(
                                originalFilename: file.originalFilename,
                                thumbnailFilename: thumbnailFilename,
                                newFilename: newFilename,
                                fileSize: file.size
                        ).save()

                        results << [
                                name: picture.originalFilename,
                                size: picture.fileSize,
                                url: createLink(controller:'image', action:'picture', id: picture.id),
                                thumbnail_url: createLink(controller:'image', action:'thumbnail', id: picture.id),
                                delete_url: createLink(controller:'image', action:'delete', id: picture.id),
                                delete_type: "DELETE"
                        ]
                    }
                }

                render results as JSON
                break;
            default: render status: HttpStatus.METHOD_NOT_ALLOWED.value()
        }
    }

    def picture(){
        def pic = Image.get(params.id)
        File picFile = new File("${grailsApplication.config.file.upload.directory?:'/tmp'}/${pic.newFilename}")
        response.contentType = 'image/jpeg'
        response.outputStream << new FileInputStream(picFile)
        response.outputStream.flush()
    }

    def thumbnail(){
        def pic = Image.get(params.id)
        File picFile = new File("${grailsApplication.config.file.upload.directory?:'/tmp'}/${pic.thumbnailFilename}")
        response.contentType = 'image/png'
        response.outputStream << new FileInputStream(picFile)
        response.outputStream.flush()
    }

    def delete(){
        def pic = Image.get(params.id)
        File picFile = new File("${grailsApplication.config.file.upload.directory?:'/tmp'}/${pic.newFilename}")
        picFile.delete()
        File thumbnailFile = new File("${grailsApplication.config.file.upload.directory?:'/tmp'}/${pic.thumbnailFilename}")
        thumbnailFile.delete()
        pic.delete()

        def result = [success: true]
        render result as JSON
    }
}

```

Browser support
===============

For more information about browser support, see [https://github.com/blueimp/jQuery-File-Upload/wiki/Browser-support](here).

Note that jquery-file-upload supports cross-domain loading but it has been disabled for now in the grails plugin,
which shouldn't be a problem.

Customize rendering
===================

Since version 1.1.0, you can now customize how the list of files is rendered by overriding a few templates. Default templates
can be found in /views/bootstrapFileUpload directory inside the plugin. Simply copy the relevant template(s) to /view/bootstrapFileUpload
in your application to override. If the taglib can find a plugin in your application, it will take precedence over the one in the plugin.

Note that /view/_upload.gsp and /view/_download.gsp are javascript templates. For more information about the syntax of those
templates, have a look at the documentation [https://github.com/blueimp/JavaScript-Templates/blob/master/README.md](here).

Note: since version 1.1.2, when you add files, they are added to the beginning of the list, not to the end, which is more natural.

Note: since version 2.0.0, you can also customize rendering for the overall form.