<template>
  <div>
    <p>
      <button v-on:click="add()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-edit blue" />
        新增
      </button>
      &nbsp;&nbsp;
      <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh green" />
        刷新
      </button>
    </p>

    <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8" />

    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr><#list fieldList as field>
        <th>${field.nameCn}</th></#list>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="${domain} in ${domain}s">
        <#list fieldList as field>
            <#if field.enums>
        <td>{{${field.enumsConst} | optionKV(${domain}.${field.nameHump})}}</td>
            <#else>
        <td>{{${domain}.${field.nameHump}}}</td>
            </#if>
        </#list>
        <div class="hidden-sm hidden-xs btn-group">
          <button v-on:click="edit(${domain})" class="btn btn-xs btn-info">
            <i class="ace-icon fa fa-pencil bigger-120"></i>
          </button>
          &nbsp;
          <button v-on:click="del(${domain}.id)" class="btn btn-xs btn-danger">
            <i class="ace-icon fa fa-trash-o bigger-120"></i>
          </button>
        </div>
      </tr>
      </tbody>
    </table>
    <!--模态框-->
    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">表单</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <#list fieldList as field>
                <#if field.name!="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt">
                  <#if field.enums>
              <div class="form-group">
                <label class="col-sm-2 control-label">${field.nameCn}</label>
                <div class="col-sm-10">
                  <select v-model="${domain}.${field.nameHump}" class="form-control">
                    <option v-for="o in ${field.enumsConst}" v-bind:value="o.key">{{o.value}}</option>
                  </select>
                </div>
              </div>
                  <#else>
              <div class="form-group">
                <label class="col-sm-2 control-label">${field.nameCn}</label>
                <div class="col-sm-10">
                  <input v-model="${domain}.${field.nameHump}" class="form-control">
                </div>
              </div>
                  </#if>
                </#if>
              </#list>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
  </div>

</template>

<script>
    import Pagination from "../../components/pagination";
    export default {
        components: {
            Pagination
        },
        name: "${module}-${domain}",
        data: function() {
            return {
                ${domain}s: [],
                ${domain}: {}, //绑定表单数据
                <#list fieldList as field>
                  <#if field.enums>
                ${field.enumsConst}: ${field.enumsConst},
                  </#if>
                </#list>
            }
        },
        mounted: function () {
            // sidebar激活样式一
            //this.$parent.activeSidebar("${module}-${domain}-sidebar")
            let _this = this;
            _this.$refs.pagination.size = 5;
            _this.list(1);

        },
        methods: {
          add() {
            let _this = this;
              _this.${domain} = {};
              //$('#form-modal').modal({backdrop: "static"});
            $('#form-modal').modal("show"); //hide
          },
          save(){
            let _this = this;

            // 保存校验
            if (1 != 1
            <#list fieldList as field>
              <#if field.name!="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt" && field.nameHump!="sort">
                <#if !field.nullAble>
              || !Validator.require(_this.${domain}.${field.nameHump}, "${field.nameCn}")
                </#if>
                <#if (field.length > 0)>
              || !Validator.length(_this.${domain}.${field.nameHump}, "${field.nameCn}", 1, ${field.length?c})
                </#if>
              </#if>
            </#list>
            ) {
              return;
            }

            _this.$ajax.post(process.env.VUE_APP_SERVER + '/${module}/admin/${domain}/save',
              _this.${domain}
            ).then((response) => {
              //console.log("保存${tableNameCn}结果： ", response);
                let responseDto = response.data;
                //let content = responseDto.content;
                if(responseDto.success){
                  $('#form-modal').modal("hide"); //hide
                  _this.list(1);
                  Toast.success("保存成功")
                } else{
                  Toast.warning(responseDto.message);
                }
            });
          },
          edit(${domain}) {
            let _this = this;
            //_this.${domain} = ${domain};
            _this.${domain} = $.extend({}, ${domain});
            $('#form-modal').modal("show");
          },
          del(id) {
              let _this = this;
              Confirm.show("确认删除?", "删除${tableNameCn}后不可恢复，是否确认？", function () {
              Loading.show();
              _this.$ajax.delete(process.env.VUE_APP_SERVER + '/${module}/admin/${domain}/delete/' + id)
                .then((response) => {
                    Loading.hide();
                    //console.log("删除${tableNameCn}列表结果", response);
                    let responseDto = response.data;
                    if(responseDto.success){
                      _this.list(1);
                      Toast.success("删除成功");
                    }
                });
            });
          },
          list(currentPage) {
            Loading.show();
            let _this = this;
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/${module}/admin/${domain}/list', {
              currentPage: currentPage,
              pageSize: _this.$refs.pagination.size
            }).then((response) => {
              Loading.hide();
              // console.log("查询${tableNameCn}列表结果", response);
              let responseDto = response.data;
              let content = responseDto.content;
              _this.${domain}s = content.list;
              _this.$refs.pagination.render(currentPage, content.total);
            });
          }
        }
    }
</script>

<style scoped>

</style>
