<template>
  <a-drawer
    placement="right"
    width="100%"
    :closable="false"
    :visible="orderShow"
    destroyOnClose
    wrapClassName="aa"
    :getContainer="false"
  >
    <a-icon type="arrow-left" style="position: absolute;z-index: 999;color: red;font-size: 20px;margin: 15px" @click="home"/>
    <dv-border-box-11 title="欢迎各位领导莅临指导" style="height: 100vh;width: 100%;background: #333333">
      <a-row style="padding: 50px;padding-top: 100px;color: white">
        <a-col :span="12">
          <a-skeleton active v-if="loading" />
          <div v-if="!loading" class="left-chart-1">
            <div class="lc1-details" style="color: white">内部检验统计</div>
            <dv-capsule-chart class="lc1-chart" :config="config" style="height: 300px"/>
          </div>
        </a-col>
        <a-col :span="12">
          <a-skeleton active v-if="loading" />
          <div v-if="!loading" class="right-chart-1">
            <div class="rc1-chart-container">
              <div class="left" style="color: white">
                <div>外部检验统计</div>
              </div>
              <dv-capsule-chart class="right" :config="config2" style="height: 300px"/>
            </div>
          </div>
        </a-col>
        <a-col :span="12" style="margin-top: 60px">
          <a-skeleton active v-if="loading" />
          <div v-if="!loading" class="left-chart-3">
            <div class="lc3-details">专家检验统计</div>
            <dv-capsule-chart class="lc3-chart" :config="config3" style="height: 300px"/>
          </div>
        </a-col>
        <a-col :span="12" style="margin-top: 60px">
          <a-skeleton active v-if="loading" />
          <div v-if="!loading" class="rc1-chart-container">
            <div class="left">
              <div>检测合格统计</div>
            </div>
            <dv-charts class="right" :option="option" style="height: 300px"/>
          </div>
        </a-col>
      </a-row>
    </dv-border-box-11>
  </a-drawer>
</template>

<script>
import LabelTag from '../component/LabelTag'
export default {
  name: 'Charts',
  components: {
    LabelTag
  },
  props: {
    orderShow: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    'orderShow': function (value) {
      if (value) {
        this.selectBoard()
      }
    }
  },
  data () {
    return {
      loading: true,
      option: {
        series: [
          {
            type: 'pie',
            data: [
              { name: '内部检验', value: 93 },
              { name: '外部检验', value: 66 },
              { name: '专家检验', value: 52 }
            ],
            radius: ['45%', '65%'],
            insideLabel: {
              show: false
            },
            outsideLabel: {
              labelLineEndLength: 10,
              formatter: '{percent}%\n{name}',
              style: {
                fontSize: 14,
                fill: '#fff'
              }
            }
          }
        ],
        color: ['#00baff', '#3de7c9', '#fff', '#ffc530', '#469f4b']
      },
      config: {
        data: [
          {
            name: '收费系统',
            value: 167
          },
          {
            name: '通信系统',
            value: 67
          },
          {
            name: '监控系统',
            value: 123
          },
          {
            name: '供配电系统',
            value: 55
          },
          {
            name: '其他',
            value: 98
          }
        ],
        colors: ['#00baff', '#3de7c9', '#fff', '#ffc530', '#469f4b'],
        unit: '条'
      },
      config2: {
        data: [
          {
            name: '收费系统',
            value: 25
          },
          {
            name: '通信系统',
            value: 66
          },
          {
            name: '监控系统',
            value: 123
          },
          {
            name: '供配电系统',
            value: 72
          },
          {
            name: '其他',
            value: 99
          }
        ],
        unit: '条'
      },
      config3: {
        data: [
          {
            name: '收费系统',
            value: 167
          },
          {
            name: '通信系统',
            value: 67
          },
          {
            name: '监控系统',
            value: 123
          },
          {
            name: '供配电系统',
            value: 55
          },
          {
            name: '其他',
            value: 98
          }
        ],
        colors: ['#00baff', '#3de7c9', '#fff', '#ffc530', '#469f4b'],
        unit: '条'
      }
    }
  },
  methods: {
    selectBoard () {
      this.loading = true
      this.$get(`/cos/materiel-info/selectBoard`).then((r) => {
        let incomeDate = []
        let incomeItem1 = {name: '接收数量', value: r.data.incomeReceive}
        let incomeItem2 = {name: '检测数量', value: r.data.incomeTotal}
        let incomeItem3 = {name: '合格数量', value: r.data.incomeQualified}
        let incomeItem4 = {name: '不合格数量', value: r.data.incomeNoQualified}
        incomeDate.push(incomeItem1)
        incomeDate.push(incomeItem2)
        incomeDate.push(incomeItem3)
        incomeDate.push(incomeItem4)
        this.config.data = incomeDate

        let processDate = []
        let processItem1 = {name: '接收数量', value: r.data.processReceive ? r.data.processReceive : 0}
        let processItem2 = {name: '检测数量', value: r.data.processTotal ? r.data.processTotal : 0}
        let processItem3 = {name: '合格数量', value: r.data.processQualified ? r.data.processQualified : 0}
        let processItem4 = {name: '不合格数量', value: r.data.processNoQualified ? r.data.processNoQualified : 0}
        processDate.push(processItem1)
        processDate.push(processItem2)
        processDate.push(processItem3)
        processDate.push(processItem4)
        this.config2.data = processDate

        let shipDate = []
        let shipItem1 = {name: '接收数量', value: r.data.shipReceive ? r.data.shipReceive : 0}
        let shipItem2 = {name: '检测数量', value: r.data.shipTotal ? r.data.shipTotal : 0}
        let shipItem3 = {name: '合格数量', value: r.data.shipQualified ? r.data.shipQualified : 0}
        let shipItem4 = {name: '不合格数量', value: r.data.shipNoQualified ? r.data.shipNoQualified : 0}
        shipDate.push(shipItem1)
        shipDate.push(shipItem2)
        shipDate.push(shipItem3)
        shipDate.push(shipItem4)
        this.config3.data = shipDate
        console.log(this.config3.data)

        let rateDate = []
        let rateItem1 = {name: '内部检验', value: r.data.incomeQualified ? r.data.incomeQualified : 0}
        let rateItem2 = {name: '外部检验', value: r.data.processQualified ? r.data.processQualified : 0}
        let rateItem3 = {name: '专家检验', value: r.data.shipQualified ? r.data.shipQualified : 0}
        rateDate.push(rateItem1)
        rateDate.push(rateItem2)
        rateDate.push(rateItem3)
        this.option.series[0].data = rateDate
        this.loading = false
      })
    },
    home () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>
>>> .ant-drawer-body {
  padding: 0 !important;
}
</style>
