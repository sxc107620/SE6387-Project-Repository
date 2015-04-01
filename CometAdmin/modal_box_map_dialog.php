<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
			<h4 class="modal-title">Route Details</h4>
		</div>
		<div class="errorMessage">
		</div>
		<div class="modal-body">
			<table class="tile table table-bordered table-striped">
            <tbody>
                <tr>
                    <td>Route Name</td>
                    <td><input autocomplete="off" class="input-sm form-control"
                    id="newRoute" maxlength="20" type="text"></td>
                </tr>
                <tr>
                    <td>Route Color</td>
                    <td>
						<div class="btn-group w100">
						  <input id="selected-color" class="input-sm form-control w88" disabled />
						  <a class="btn btn-mini dropdown-toggle left" data-toggle="dropdown">Color</a>
						  <ul class="dropdown-menu palette">
							<li><div id="colorpalette1"></div></li>
						  </ul>
						</div>
					</td>
                </tr>
            </tbody>
        </table>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-sm" id="openEditor">Save</button>
			<button type="button" class="btn btn-sm" data-dismiss="modal">Close</button>
		</div>
	</div>
</div>
