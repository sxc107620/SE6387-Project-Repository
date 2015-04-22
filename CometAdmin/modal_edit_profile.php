<div class="modal fade" id="modalEditProfile" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
				<h4 class="modal-title"><?php echo ucfirst($_SESSION['uName'])."'s Profile";?></h4>
			</div>
			<div class="errorMessage">
			</div>
			<form id="imgInfo"  method="post" enctype="multipart/form-data">
			<div class="modal-body">
				<table class="tile table table-bordered table-striped">
					<tbody>
						<tr>	
							<td>User Name</td>
							<td><?php echo ucfirst($_SESSION['uName']) ?></td>
						</tr>
						<tr>
							<td>Email</td>
							<td><?php echo $_SESSION['email'] ?></td>
						</tr>
						<tr>
							<td>Status</td>
							<td><?php echo ucfirst($_SESSION['type']) ?></td>
						</tr>	
						<tr>
							<td>Password</td>
							<td><input type="password" id="newPassChange" class="input-sm form-control" name="password"></td>
						</tr>
						<tr>
							<td>Confirm Password</td>
							<td><input type="password" id="newCPassChange" class="input-sm form-control"></td>
						</tr>
						<tr>
							<td>Profile Image</td>
							<td>
							
								<div class="fileupload fileupload-new" data-provides="fileupload"><input type="hidden" value="" name="">
									<div class="fileupload-preview thumbnail form-control"></div>
									
									<div>
										<span class="btn btn-file btn-alt btn-sm">
											<span class="fileupload-new">Select image</span>
											<span class="fileupload-exists">Change</span>
											<input type="file" id="displayPic" name='profilePic'>
										</span>
										<a href="#" class="btn fileupload-exists btn-sm" data-dismiss="fileupload">Remove</a>
									</div>
								</div>
							
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="modal-footer">
				<input type="submit" class="btn btn-sm" id="editProfileSave" value="Save" />
				<button type="button" class="btn btn-sm" data-dismiss="modal">Close</button>
			</div>
			</form>	
		</div>
	</div>
</div>