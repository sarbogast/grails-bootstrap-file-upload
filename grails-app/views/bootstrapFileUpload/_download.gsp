%{--
  - Copyright 2012 Sebastien Arbogast
  -
  -    Licensed under the Apache License, Version 2.0 (the "License");
  -    you may not use this file except in compliance with the License.
  -    You may obtain a copy of the License at
  -
  -        http://www.apache.org/licenses/LICENSE-2.0
  -
  -    Unless required by applicable law or agreed to in writing, software
  -    distributed under the License is distributed on an "AS IS" BASIS,
  -    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  -    See the License for the specific language governing permissions and
  -    limitations under the License.
  --}%
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
    {% } else { %}
    {%=file.error%}
    {% } %}
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
  </td>"""
</tr>
{% } %}
