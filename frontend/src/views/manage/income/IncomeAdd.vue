<template>
  <a-modal v-model="show" title="新增内部检测信息" @cancel="onClose" :width="950">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
        提交
      </a-button>
    </template>
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="8">
          <a-form-item label='检验单名称' v-bind="formItemLayout">
            <a-input v-decorator="[
            'name',
            { rules: [{ required: true, message: '请输入检验单名称!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-divider orientation="left">
          <span style="font-size: 13px;font-family: SimHei">模块与机构</span>
        </a-divider>
        <a-col :span="6">
          <a-form-item label='选择模块' v-bind="formItemLayout">
            <a-select @change="materielChange" v-decorator="[
              'materielId',
              { rules: [{ required: true, message: '请输入模块!' }] }
              ]">
              <a-select-option :value="item.id" v-for="(item, index) in materielList" :key="index">{{ item.name }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label='产品模块编码' disabled v-bind="formItemLayout">
            <a-input v-decorator="[
            'materielCode',
            { rules: [{ required: true, message: '请输入产品模块!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label='产品模块名称' disabled v-bind="formItemLayout">
            <a-input v-decorator="[
            'materielName',
            { rules: [{ required: true, message: '请输入产品模块名称!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label='模块等级' disabled v-bind="formItemLayout">
            <a-input v-decorator="[
            'unit'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='机构' v-bind="formItemLayout">
            <a-select @change="supplierChange" v-decorator="[
              'supplierId',
              { rules: [{ required: true, message: '请输入机构!' }] }
              ]">
              <a-select-option :value="item.id" v-for="(item, index) in supplierList" :key="index">{{ item.name }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='机构简称' disabled v-bind="formItemLayout">
            <a-input v-decorator="[
            'abbreviation'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='机构批次号' disabled v-bind="formItemLayout">
            <a-input v-decorator="[
            'batchNumber',
            ]"/>
          </a-form-item>
        </a-col>
        <a-divider orientation="left">
          <span style="font-size: 13px;font-family: SimHei">检测情况</span>
        </a-divider>
        <a-col :span="8">
          <a-form-item label='本次检测数量' disabled v-bind="formItemLayout">
            <a-input-number :min="0" :max="99999" v-decorator="[
            'receiveQuantity',
            { rules: [{ required: true, message: '请输入本次检测数量!' }] }
            ]" style="width: 100%"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='内部日期' disabled v-bind="formItemLayout">
            <a-date-picker style="width: 100%;" v-decorator="[
            'incomingDate',
            { rules: [{ required: true, message: '请输入内部日期!' }] }
            ]"/>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
import moment from 'moment'
moment.locale('zh-cn')
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'incomeAdd',
  props: {
    incomeAddVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.incomeAddVisiable
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      pharmacyList: [],
      shopList: [],
      incomeTypeList: [],
      brandList: [],
      materielList: [],
      supplierList: []
    }
  },
  mounted () {
    this.selectMaterielList()
    this.selectSupplierList()
  },
  methods: {
    materielChange (value) {
      if (value) {
        this.materielList.forEach(item => {
          if (item.id === value) {
            this.form.setFieldsValue({
              materielCode: item.code,
              materielName: item.name,
              unit: item.unit
            })
          }
        })
      }
    },
    supplierChange (value) {
      if (value) {
        this.supplierList.forEach(item => {
          if (item.id === value) {
            this.form.setFieldsValue({
              abbreviation: item.abbreviation
            })
          }
        })
      }
    },
    selectMaterielList () {
      this.$get(`/cos/detection-template-info/materiel`, {type: '1'}).then((r) => {
        this.materielList = r.data.data
      })
    },
    selectSupplierList () {
      this.$get(`/cos/supplier-info/list`).then((r) => {
        this.supplierList = r.data.data
      })
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        // 获取图片List
        let images = []
        this.fileList.forEach(image => {
          images.push(image.response)
        })
        if (!err) {
          values.images = images.length > 0 ? images.join(',') : null
          values.incomingDate = moment(values.incomingDate).format('YYYY-MM-DD')
          this.loading = true
          this.$post('/cos/income-check-info', {
            ...values
          }).then((r) => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
