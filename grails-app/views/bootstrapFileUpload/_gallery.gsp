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
<!-- modal-gallery is the modal dialog used for the image gallery -->
<div id="modal-gallery" class="modal modal-gallery hide fade">
  <div class="modal-header">
    <a class="close" data-dismiss="modal">&times;</a>
    <h3 class="modal-title"></h3>
  </div>
  <div class="modal-body"><div class="modal-image"></div></div>
  <div class="modal-footer">
    <a class="btn modal-download" target="_blank">
      <i class="icon-download"></i>
      <span>Download</span>
    </a>
    <a class="btn btn-success modal-play modal-slideshow" data-slideshow="5000">
      <i class="icon-play icon-white"></i>
      <span>Slideshow</span>
    </a>
    <a class="btn btn-info modal-prev">
      <i class="icon-arrow-left icon-white"></i>
      <span>Previous</span>
    </a>
    <a class="btn btn-primary modal-next">
      <span>Next</span>
      <i class="icon-arrow-right icon-white"></i>
    </a>
  </div>
</div>
