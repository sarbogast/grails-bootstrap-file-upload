package org.grails.plugins.bootstrap.file.upload

import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.MultipartFile
import grails.converters.JSON

class ImageController {

    def index() { }

    def upload() {
        def results = []
        if (request instanceof MultipartHttpServletRequest){
            for(filename in request.getFileNames()){
                MultipartFile file = request.getFile(filename)

                String newFileName = UUID.randomUUID().toString() + file.originalFilename.substring(file.originalFilename.lastIndexOf("."))

                file.transferTo(new File("/tmp/$newFileName"))

                results << [
                        name: file.originalFilename,
                        size: file.size,
                        url: createLink(controller: 'image', action: 'image'),
                        thumbnail_url: createLink(controller: 'image', action: 'image'),
                        delete_url: createLink(controller: 'image', action: 'image'),
                        delete_type: "DELETE"
                ]
            }
        }

        render results as JSON
    }

    def image(){
        render ""
    }
}
