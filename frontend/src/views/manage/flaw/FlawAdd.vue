<template>
  <a-modal v-model="show" title="新增常见缺陷信息" @cancel="onClose" :width="800">
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
        <a-col :span="12">
          <a-form-item label='常见缺陷名称' v-bind="formItemLayout">
            <a-input v-decorator="[
            'name',
            { rules: [{ required: true, message: '请输入常见缺陷名称!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='检测项类型' v-bind="formItemLayout">
            <a-select v-decorator="[
              'detectionItem',
              { rules: [{ required: true, message: '请输入检测项类型!' }] }
              ]">
              <a-select-option value="1">性能测试</a-select-option>
              <a-select-option value="2">并发测试</a-select-option>
              <a-select-option value="3">压力测试</a-select-option>
              <a-select-option value="4">环境测试</a-select-option>
              <a-select-option value="5">流程测试</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='缺陷等级' v-bind="formItemLayout">
            <a-select v-decorator="[
              'flawLevel',
              { rules: [{ required: true, message: '请输入缺陷等级!' }] }
              ]">
              <a-select-option value="1">致命缺陷</a-select-option>
              <a-select-option value="2">严重缺陷</a-select-option>
              <a-select-option value="3">轻微缺陷</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='缺陷描述' v-bind="formItemLayout">
            <a-textarea :rows="4" v-decorator="[
            'content'
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
  name: 'flawAdd',
  props: {
    flawAddVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.flawAddVisiable
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
      flawTypeList: [],
      brandList: []
    }
  },
  methods: {
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
          this.loading = true
          this.$post('/cos/flaw-info', {
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
