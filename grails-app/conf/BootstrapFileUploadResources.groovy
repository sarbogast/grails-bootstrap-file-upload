modules = {
    'bootstrap-image-gallery' {
        dependsOn 'bootstrap', 'blueimp-load-image'

        resource url: [plugin: 'bootstrap-file-upload', dir: 'css', file: 'bootstrap-image-gallery.css']
        resource url: [plugin: 'bootstrap-file-upload', dir: 'js', file: 'bootstrap-image-gallery.js']
    }

    'blueimp-templates' {
        resource url: [plugin: 'bootstrap-file-upload', dir: 'js', file: 'tmpl.js']
    }

    'blueimp-load-image' {
        resource url: [plugin: 'bootstrap-file-upload', dir: 'js', file: 'load-image.js']
    }

    'blueimp-canvas-to-blob' {
        resource url: [plugin: 'bootstrap-file-upload', dir: 'js', file: 'canvas-to-blob.js']
    }

    'bootstrap-file-upload' {
        dependsOn 'jquery-ui', 'blueimp-templates', 'blueimp-canvas-to-blob', 'bootstrap-image-gallery'

        resource url: [plugin: 'bootstrap-file-upload', dir: 'css', file: 'jquery.fileupload-ui.css']

        resource url: [plugin: 'bootstrap-file-upload', dir: 'js', file: 'jquery.iframe-transport.js']
        resource url: [plugin: 'bootstrap-file-upload', dir: 'js', file: 'jquery.fileupload.js']
        resource url: [plugin: 'bootstrap-file-upload', dir: 'js', file: 'jquery.fileupload-ip.js']
        resource url: [plugin: 'bootstrap-file-upload', dir: 'js', file: 'jquery.fileupload-ui.js']
    }
}