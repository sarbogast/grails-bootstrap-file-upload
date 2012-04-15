modules = {
    'bootstrap-image-gallery' {
        dependsOn 'bootstrap'

        resource url: '/css/bootstrap-image-gallery.css'

        resource url: '/js/bootstrap-image-gallery.js'
    }

    'blueimp-templates' {
        resource url: '/js/tmpl.js'
    }

    'blueimp-load-image' {
        resource url: '/js/load-image.js'
    }

    'blueimp-canvas-to-blob' {
        resource url: '/js/canvas-to-blob.js'
    }

    'bootstrap-file-upload' {
        dependsOn 'jquery-ui', 'blueimp-templates', 'blueimp-load-image', 'blueimp-canvas-to-blob'

        resource url: '/css/jquery.fileupload-ui.css'

        resource url: '/js/jquery.iframe-transport.js'
        resource url: '/js/jquery.fileupload.js'
        resource url: '/js/jquery.fileupload-ip.js'
        resource url: '/js/jquery.fileupload-ui.js'
        resource url: '/js/locale.js'
        resource url: '/js/main.js'
        resource url: '/js/cors/jquery.xdr-transport.js'
    }
}