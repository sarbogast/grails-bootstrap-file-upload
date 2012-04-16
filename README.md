Description
===========
This plugin integrates the excellent Sebastian Tschan's jquery-file-upload (https://github.com/blueimp/jQuery-File-Upload)
into grails 2.0. To view a nice demo of Sebastian's work, [http://blueimp.github.com/jQuery-File-Upload/](click here).

It integrates nicely with Twitter Bootstrap and is completely internationalizable.

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
* controller: the controller to send files to (required)
* action: the action to send files to (required)
* maxFileSize: Maximum file size (optional, defaults to 5000000, ie 5MB)
* acceptFileTypes: regular expression for accepted file type (optional, defaults to /(\.|\/)(gif|jpe?g|png)$/i)
* resizeMaxWidth: maximum width images will be resized to in supporting browsers (optional, defaults to 1920)
* resizeMaxHeight: maximum height images will be resized to in supporting browsers (optional, defaults to 1200)
* params: map containing parameters that will be sent to the controller on upload (optional, defaults to [:])
* allowDelete: boolean indicating whether delete is allowed (optional, defaults to true)

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