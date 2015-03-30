<div class="modal-dialog mapModal">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
			<h4 class="modal-title">Map Editor</h4>
		</div>
		<div class="errorMessage">
		</div>
		<div class="modal-body">
			<div class="toolBar">
				<div class="toolIcon">
					<div class="toolIconBG" id="drag" data-toggle="tooltip" data-placement="left" title="Drag Map"> 
						<div class="toolDrag tool"></div>
					</div>
					<div class="toolIconBG" id="line" data-toggle="tooltip" data-placement="left" title="Draw Lines">
						<div class="toolLine tool"></div>
					</div>
					<div class="toolIconBG" id="curve" data-toggle="tooltip" data-placement="left" title="Draw Curves">
						<div class="toolCurve tool"></div>
					</div>
					<!--div class="toolIconBG" id="eraser" data-toggle="tooltip" data-placement="left" title="Eraser">
						<div class="toolEraser tool"></div>
					</div-->
					<div class="toolIconBG" id="marker" data-toggle="tooltip" data-placement="left" title="Safe Spots">
						<div class="toolMarker tool"></div>
					</div>
					<div class="toolIconBG" id="undo" data-toggle="tooltip" data-placement="left" title="Undo">
						<div class="toolUndo tool"></div>
					</div>
					<div class="toolIconBG" id="redo" data-toggle="tooltip" data-placement="left" title="Redo">
						<div class="toolRedo tool"></div>
					</div>
					<div class="toolIconBG" id="clear" data-toggle="tooltip" data-placement="left" title="Clear">
						<div class="toolClear tool"></div>
					</div>
				</div>
			</div>
			<div id="editMap" class='routeMapEdit'></div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-sm">Save</button>
			<button type="button" class="btn btn-sm" data-dismiss="modal">Close</button>
		</div>
	</div>
</div>
