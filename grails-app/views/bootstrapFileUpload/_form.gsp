<form id="${id}" action="${url}" method="${type}" ${multipart ? 'enctype="multipart/form-data"' : ''}>
  <g:each in="${formData}" var="entry">
    <g:hiddenField name="${entry.key}" value="${entry.value}"/>
  </g:each>

  <div class="fileupload-buttonbar">
    <div>
      <span class="btn btn-success fileinput-button">
        <i class="icon-plus icon-white"></i>
        <span>${message(code: 'fileupload.add.files', default: 'Add files...')}</span>
        <input type="file" name="${paramName}" multiple>
      </span>
      <g:if test="${!autoUpload}">
      <button type="submit" class="btn btn-primary start">
        <i class="icon-upload icon-white"></i>
        <span>${message(code: 'fileupload.start.upload', default: 'Start upload')}</span>
      </button>
      <button type="reset" class="btn btn-warning cancel">
        <i class="icon-ban-circle icon-white"></i>
        <span>${message(code: 'fileupload.cancel.upload', default: 'Cancel upload')}</span>
      </button>
      </g:if>
      <button type="button" class="btn btn-danger delete">
        <i class="icon-trash icon-white"></i>
        <span>${message(code: 'fileupload.delete', default: 'Delete')}</span>
      </button>
      <input id="${id}-toggle" type="checkbox" class="toggle">&nbsp;<label style="display:inline" for="${id}-toggle">${message(code: 'fileupload.select.all', default: 'Select all')}</label>
    </div>
    <div>
      <!-- The global progress bar -->
      <div class="progress progress-success progress-striped active fade" style="margin-bottom:-6px; margin-top:6px;">
        <div class="bar" style="width:0%;"></div>
      </div>
    </div>
  </div>
  <!-- The loading indicator is shown during image processing -->
  <div class="fileupload-loading"></div>
  <br>

  ${list}

</form>

