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
<!-- The table listing the files available for upload/download -->
<table class="table table-striped">
  <tbody ${filesContainer ? (filesContainer.startsWith('.') ? 'class="' + filesContainer.substring(1) + '"' : 'id="' + filesContainer.substring(1) + '"') : 'class="files"'} data-toggle="modal-gallery" data-target="#modal-gallery">

  </tbody>
</table>
