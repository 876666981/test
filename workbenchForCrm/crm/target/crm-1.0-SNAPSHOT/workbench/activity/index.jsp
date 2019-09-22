<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>


	<script type="text/javascript">

	$(function(){


		pageList(1, 2);

		$("#searchBtn").click(function () {
			$("#hiden-endDate").val($("#search-endDate").val());
			$("#hiden-startDate").val($("#search-startDate").val());
			$("#hiden-owner").val($("#search-owner").val());
			$("#hiden-name").val($("#search-name").val());
			pageList(1,2);
		})
		$(".time").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayBtn: true,
			pickerPosition: "bottom-left"
		});
		$("#addBtn").click(function () {
			$.ajax({
				url:"workbench/activity/getUserListNameByID.do",
				type:"get",
				dataType:"json",
				success:function (data) {
					var html = "";
					$.each(data,function (i,n) {
						html += "<option value='"+n.id+"'>"+n.name+"</option>";
					})
					$("#create-marketActivityOwner").html(html);

					$("#create-marketActivityOwner").val("${sessionScope.user.id}");

					$("#createActivityModal").modal("show");
				}

			})

		});
		//批量删除市场活动
		$("#deleteBtn").click(function () {
			var ids = $("input[name=xz]:checked");
			if (ids.length > 0){
				if (confirm("确定删除所选记录吗?")){
					var param = "";
					for (var i = 0; i < ids.length; i++){

						param += "id="+$(ids[i]).val();
						if (i < ids.length-1){
							param += "&"
						}

					}
					console.log(param);
					$.ajax({
						url:"workbench/activity/delete.do",
						data:param,
						type:"post",
						dataType:"json",
						success:function (data) {
							console.log(data)
							if (data.success){
								pageList(1,2);
							} else{
								alert("市场活动删除失败");
							}
						}
					})
				}

			} else{
				alert("请选择您要删除的记录");
			}

		})
		//执行市场活动修改
		$("#updateBtn").click(function () {

			$.ajax({
				url:"workbench/activity/update.do",
				data:{
					"id":$("#edit-ActivityID").val(),
					"owner":$.trim($("#edit-marketActivityOwner").val()),
					"name":$.trim($("#edit-marketActivityName").val()),
					"startDate":$("#edit-startTime").val(),
					"endDate":$("#edit-endTime").val(),
					"cost":$.trim($("#edit-cost").val()),
					"description":$.trim($("#edit-describe").val())
				},
				type:"post",
				dataType:"json",
				success:function (data) {
					if (data.success) {
						pageList(1, 2);
						$("#editActivityModal").modal("hide");
					}else{
						alert("修改市场活动失败");
					}

				}
			})
		})
		//执行市场活动添加
		$("#saveBtn").click(function () {
				$.ajax({
                    url:"workbench/activity/save.do",
                    data:{
                        "owner":$.trim($("#create-marketActivityOwner").val()),
                        "name":$.trim($("#create-marketActivityName").val()),
                        "startDate":$("#create-startTime").val(),
                        "endDate":$("#create-endTime").val(),
                        "cost":$.trim($("#create-cost").val()),
                        "description":$.trim($("#create-describe").val())
                    },
                    type:"post",
                    dataType:"json",
                    success:function (data) {
                        if (data.success) {
							pageList(1, 2);
							$("#activityForm")[0].reset();
							$("#createActivityModal").modal("hide");
						}else{
							alert("添加市场活动失败");
						}

                    }
                })
		});

		$("#qx").click(function () {
			$("input[name=xz]").prop("checked",this.checked)
		})
		$("#activityBody").on("click",$("input[name=xz]"),function () {
			$("#qx").prop("checked",$("input[name=xz]").length == $("input[name=xz]:checked").length)
		})
	})

	//局部刷新市场活动列表
	function pageList(pageNo,pageSize) {
		$("#qx").prop("checked",false);
		$("#search-name").val($("#hiden-name").val());
		$("#search-owner").val($("#hiden-owner").val());
		$("#search-startDate").val($("#hiden-startDate").val());
		$("#search-endDate").val($("#hiden-endDate").val());
		$.ajax({
			url:"workbench/activity/pageList.do",
			data : {

				"name" : $.trim($("#search-name").val()),
				"owner" : $.trim($("#search-owner").val()),
				"startDate" : $.trim($("#search-startDate").val()),
				"endDate" : $.trim($("#search-endDate").val()),
				"pageNo" : pageNo,
				"pageSize" : pageSize

			},
			type : "get",
			dataType : "json",
			success:function (data) {
				console.log(data);
				//var str = "'\"\"'";
				//var str = '"\'\'"';

				//n:市场活动的json对象
				var html = "";
				$.each(data.dataList,function (i,n) {

					html += '<tr class="active">';
					html += '<td><input type="checkbox" name="xz" value="'+n.id+'"/></td>';
					html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.do?id='+n.id+'\';">'+n.name+'</a></td>';
					html += '<td>'+n.owner+'</td>';
					html += '<td>'+n.startDate+'</td>';
					html += '<td>'+n.endDate+'</td>';
					html += '<td><button type="button" class="btn btn-default" onclick="edit('+"'"+n.id+"'"+')"><span class="glyphicon glyphicon-pencil"></span></button></td>';
					html += '</tr>';

				})

				//将以上所有拼接好的tr和td往tBody标签对里塞
				$("#activityBody").html(html);
				var totalPages = data.total%pageSize == 0 ? data.total/pageSize : parseInt(data.total/pageSize)+1;
				$("#activityPage").bs_pagination({
					currentPage: pageNo, // 页码
					rowsPerPage: pageSize, // 每页显示的记录条数
					maxRowsPerPage: 10, // 每页最多显示的记录条数
					totalPages: totalPages, // 总页数
					totalRows: data.total, // 总记录条数

					visiblePageLinks: 3, // 显示几个卡片

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,

					onChangePage : function(event, data){
						pageList(data.currentPage , data.rowsPerPage);
					}
				});

			}
		})
	}
</script>
</head>
<body>
<script>
	function edit(id){
		$.ajax({
			url:"workbench/activity/selectByID.do",
			data:{
				id:id
			},
			type:"get",
			dataType:"json",
			success:function (data) {
				console.log(data);
				var html = "";
				$.each(data.userList,function (i,n) {
					html += "<option value='"+n.id+"'>"+n.name+"</option>";
				})
				$("#edit-marketActivityOwner").html(html);
				$("#edit-ActivityID").val(id);
				$("#edit-marketActivityOwner").val(data.activityFind.owner);
				$("#edit-marketActivityName").val(data.activityFind.name);
				$("#edit-startTime").val(data.activityFind.startDate);
				$("#edit-endTime").val(data.activityFind.endDate);
				$("#edit-cost").val(data.activityFind.cost);
				$("#edit-describe").val(data.activityFind.description);
				$("#editActivityModal").modal("show");

			}
		})
	}
</script>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form" id="activityForm">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">
								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startTime" readonly>
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endTime" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<input type="hidden" id="edit-ActivityID">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">

								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-startTime" readonly>
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-endTime" readonly>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe"></textarea>
							</div>
						</div>

					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
						<input type="hidden" id="hiden-name">
				      <input class="form-control" type="text" id="search-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
						<input type="hidden" id="hiden-owner">
				      <input class="form-control" type="text" id="search-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
						<input type="hidden" id="hiden-startDate">
					  <input class="form-control" type="text" id="search-startDate" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
						<input type="hidden" id="hiden-endDate">
					  <input class="form-control" type="text" id="search-endDate">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="searchBtn">查询</button>

				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" id="addBtn" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" id="deleteBtn" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qx" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
							<td>修改</td>
						</tr>
					</thead>
					<tbody id="activityBody">
						<%--<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
                            <td>2020-10-10</td>
                            <td>2020-10-20</td>
                        </tr>--%>
					</tbody>
				</table>
			</div>
			
			<div id="activityPage"></div>
			
		</div>
		
	</div>

</body>
</html>