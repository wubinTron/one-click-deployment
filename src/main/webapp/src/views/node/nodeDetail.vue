<template>
  <div class="app-container">
    <div class="topButton">
      <el-button size="mini" @click="showSR()" type="danger">SR节点</el-button>


      <el-button size="mini" @click="showFullNode()" type="primary">Full Node节点</el-button>
      <!-- <el-button size="mini" type="warning" @click="showChange()">修改</el-button> -->

      <!-- <el-button size="mini" type="info" @click="mShow()">是否为witeness</el-button> -->

      <!-- <el-button size="mini" type="danger" @click="showDelete()">删除</el-button> -->
    </div>

    <h5>基础信息</h5>
    <div class="message">
      <el-row :gutter="20" >
        <el-col :span="4">
          <div>
            <el-card style="position: relative;" class="elcard1">
              <div class="card">
                <div class="something">
                  <span class="s1">3243</span>
                  <span class="s2">Begin Sync Num</span>
                  <!-- <i class="el-icon-aim" style="color: #00CCFF;"></i> -->
                </div>

                <!-- <el-progress :percentage="0"></el-progress> -->
              </div>
            </el-card>

          </div>
        </el-col>

        <el-col :span="4">
          <div>
            <el-card style="position: relative;" class="elcard1">
              <div class="card">
                <div class="something">
                  <!-- <span class="s1">0</span> -->
                  <span class="s1">3</span>
                  <span class="s2">当前连接数</span>
                  <!-- <i class="el-icon-truck" style=" color:#EB7347"></i> -->
                </div>

                <!-- <el-progress :percentage="0" color="skyblue"></el-progress> -->
              </div>
            </el-card>
          </div>
        </el-col>

        <el-col :span="4">
          <div>
            <el-card style="position: relative;" class="elcard1">
              <div class="card">
                <div class="something">
                  <span class="s1">2</span>
                  <span class="s2">主动连接数</span>
                  <!-- <i class="el-icon-user" style="color: #26A65B"></i> -->
                </div>

                <!-- <el-progress :percentage="0" color="#FC9D99"></el-progress> -->
              </div>
            </el-card>
          </div>
        </el-col>

        <el-col :span="4">
          <div>
            <el-card style="position: relative;" class="elcard1">
              <div class="card">
                <div class="something">
                  <span class="s1">1</span>
                  <span class="s2">被动连接数</span>
                  <!-- <i class="el-icon-user" style="color: #26A65B"></i> -->
                </div>

                <!-- <el-progress :percentage="0" color="#FC9D99"></el-progress> -->
              </div>
            </el-card>
          </div>
        </el-col>

        <el-col :span="4">
          <div>
            <el-card style="position: relative;" class="elcard1">
              <div class="card">
                <div class="something">
                  <span class="s1">644774</span>
                  <span class="s2">当前块Num</span>
                  <!-- <i class="el-icon-user" style="color: #26A64B"></i> -->
                </div>

                <!-- <el-progress :percentage="0" color="#FC9D99"></el-progress> -->
              </div>
            </el-card>
          </div>
        </el-col>

        <el-col :span="4">
          <div>
            <el-card style="position: relative;" class="elcard1">
              <div class="card">
                <div class="something">
                  <span class="s1">444324</span>
                  <span class="s2">固化块Num</span>
                  <!-- <i class="el-icon-user" style="color: #24A44B"></i> -->
                </div>

                <!-- <el-progress :percentage="0" color="#FC9D99"></el-progress> -->
              </div>
            </el-card>
          </div>
          </el-col>

      </el-row>


    </el-row>
    <h5>配置信息</h5>
    <el-row :gutter="20" >

      <el-col :span="4">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">5</span>
                <span class="s2">Active Node Size</span>
              </div>
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="4">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">1000</span>
                <span class="s2">Backup Listen Port</span>
              </div>
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="4">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">1</span>
                <span class="s2">Backup Member Size</span>
              </div>
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="4">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">8888</span>
                <span class="s2">Listen Port</span>
              </div>
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="4">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">1000</span>
                <span class="s2">Backup Listen Port</span>
              </div>
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="4">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">30</span>
                <span class="s2">Max Connect Count</span>
              </div>
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="4">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">0</span>
                <span class="s2">Passive Node Size</span>
              </div>
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="5">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">0</span>
                <span class="s2">Same IP Max Connect Count</span>
              </div>
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="5">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">3</span>
                <span class="s2">Send Node Size</span>
              </div>
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="5">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">v1</span>
                <span class="s2">p2p versione</span>
              </div>
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="5">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">v2</span>
                <span class="s2">code versione</span>
              </div>
            </div>
          </el-card>
        </div>
      </el-col>

    </el-row>

    <h5>机器信息</h5>
    <el-row :gutter="20" >
      <el-col :span="5">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">3Gb</span>
                <span class="s2">Total Memory</span>
                <!-- <i class="el-icon-aim" style="color: #00CCFF;"></i> -->
              </div>

              <!-- <el-progress :percentage="0"></el-progress> -->
            </div>
          </el-card>

        </div>
      </el-col>

      <el-col :span="5">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <!-- <span class="s1">0</span> -->
                <span class="s1">116Mb</span>
                <span class="s2">Free Memory</span>
                <!-- <i class="el-icon-truck" style=" color:#EB7347"></i> -->
              </div>

              <!-- <el-progress :percentage="0" color="skyblue"></el-progress> -->
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="5">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">2Gb</span>
                <span class="s2">JVM Free Memory</span>
                <!-- <i class="el-icon-user" style="color: #26A65B"></i> -->
              </div>

              <!-- <el-progress :percentage="0" color="#FC9D99"></el-progress> -->
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="4">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">3</span>
                <span class="s2">CPU count</span>
                <!-- <i class="el-icon-user" style="color: #26A65B"></i> -->
              </div>

              <!-- <el-progress :percentage="0" color="#FC9D99"></el-progress> -->
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="5">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">64</span>
                <span class="s2">Thread Count</span>
                <!-- <i class="el-icon-user" style="color: #26A64B"></i> -->
              </div>

              <!-- <el-progress :percentage="0" color="#FC9D99"></el-progress> -->
            </div>
          </el-card>
        </div>
      </el-col>
    </el-row>

    <h5> 连接节点信息</h5>
    <el-row :gutter="20" >
      <el-col :span="5">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">34</span>
                <span class="s2">Avg Latancy</span>
                <!-- <i class="el-icon-aim" style="color: #00CCFF;"></i> -->
              </div>

              <!-- <el-progress :percentage="0"></el-progress> -->
            </div>
          </el-card>

        </div>
      </el-col>

      <el-col :span="5">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <!-- <span class="s1">0</span> -->
                <span class="s1">1</span>
                <span class="s2">Block in Proc Size</span>
                <!-- <i class="el-icon-truck" style=" color:#EB7347"></i> -->
              </div>

              <!-- <el-progress :percentage="0" color="skyblue"></el-progress> -->
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="5">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">0</span>
                <span class="s2">Disconnect Time</span>
                <!-- <i class="el-icon-user" style="color: #26A65B"></i> -->
              </div>

              <!-- <el-progress :percentage="0" color="#FC9D99"></el-progress> -->
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="4">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">11</span>
                <span class="s2">Node count</span>
                <!-- <i class="el-icon-user" style="color: #26A65B"></i> -->
              </div>

              <!-- <el-progress :percentage="0" color="#FC9D99"></el-progress> -->
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="5">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">8080</span>
                <span class="s2">Port</span>
                <!-- <i class="el-icon-user" style="color: #26A64B"></i> -->
              </div>

              <!-- <el-progress :percentage="0" color="#FC9D99"></el-progress> -->
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="5">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">8B</span>
                <span class="s2">五分钟入口流量</span>
                <!-- <i class="el-icon-user" style="color: #26A64B"></i> -->
              </div>

              <!-- <el-progress :percentage="0" color="#FC9D99"></el-progress> -->
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="5">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">0</span>
                <span class="s2">Sync Block Requested Size</span>
                <!-- <i class="el-icon-user" style="color: #26A64B"></i> -->
              </div>

              <!-- <el-progress :percentage="0" color="#FC9D99"></el-progress> -->
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="7">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">120.79.211.213</span>
                <span class="s2">Host</span>
                <!-- <i class="el-icon-user" style="color: #26A64B"></i> -->
              </div>

              <!-- <el-progress :percentage="0" color="#FC9D99"></el-progress> -->
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="20">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">3w5342546457657654uy6rtuy65476547654xs</span>
                <span class="s2">Node ID</span>
                <!-- <i class="el-icon-user" style="color: #26A64B"></i> -->
              </div>

              <!-- <el-progress :percentage="0" color="#FC9D99"></el-progress> -->
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="10">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">1999-11-10 13:12:11</span>
                <span class="s2">Connect Time</span>
                <!-- <i class="el-icon-user" style="color: #26A64B"></i> -->
              </div>

              <!-- <el-progress :percentage="0" color="#FC9D99"></el-progress> -->
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="10">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">2000-11-10 14:43:13</span>
                <span class="s2">Head Block Time We both have</span>
                <!-- <i class="el-icon-user" style="color: #26A64B"></i> -->
              </div>

              <!-- <el-progress :percentage="0" color="#FC9D99"></el-progress> -->
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="6">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">2</span>
                <span class="s2">Head Block Time We both have Num</span>
                <!-- <i class="el-icon-user" style="color: #26A64B"></i> -->
              </div>

              <!-- <el-progress :percentage="0" color="#FC9D99"></el-progress> -->
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="10">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">2018-12-10 14:43:13</span>
                <span class="s2">Last Block Update Time</span>
                <!-- <i class="el-icon-user" style="color: #26A64B"></i> -->
              </div>

              <!-- <el-progress :percentage="0" color="#FC9D99"></el-progress> -->
            </div>
          </el-card>
        </div>
      </el-col>

      <el-col :span="6">
        <div>
          <el-card style="position: relative;" class="elcard1">
            <div class="card">
              <div class="something">
                <span class="s1">2343234</span>
                <span class="s2">Last Sync Block Num</span>
                <!-- <i class="el-icon-user" style="color: #26A64B"></i> -->
              </div>

              <!-- <el-progress :percentage="0" color="#FC9D99"></el-progress> -->
            </div>
          </el-card>
        </div>
      </el-col>

    </el-row>



    </div>

    <!-- <div class="dataChart">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-card>
            <div id="myChart" :style="{width: '100%', height: '400px'}"></div>
          </el-card>
        </el-col>

        <el-col :span="12">
          <el-card>
            <div id="myChart2" :style="{width: '100%', height: '400px'}"></div>
          </el-card>
        </el-col>
      </el-row>
    </div> -->

    <!-- <div class="welcome">
      <el-card class="elcard20">
        <div class="text2">WELCOME</div>
      </el-card>
    </div> -->
  </div>
</template>

<script>
export default {
  data() {
    return {
      value: new Date(),
      materialL: "",
      userL: ""
    };
  },
  components: {},
  created() {
    let _this = this;
    this.$axios
      .get("/materials")
      .then(function(res) {
        if (res.data) {
          _this.materialL = res.data.length;
          // console.log(_this.materialL);
        }
      })
      .catch(function(err) {
        if (err.response) {
          console.log(err.response);
        }
      });

    this.$axios
      .get("/user")
      .then(function(res) {
        if (res.data) {
          _this.userL = res.data.length;
          // console.log(res.data)
        }
      })
      .catch(function(err) {
        if (err.response) {
          console.log(err.response);
        }
      });
  },
  computed: {},
  methods: {
    format(percentage) {
      return percentage === 100 ? "满" : `${percentage}`;
    },
    drawLine() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById("myChart"));
      // 绘制图表
      myChart.setOption({
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross",
            crossStyle: {
              color: "#999"
            }
          }
        },
        toolbox: {
          feature: {
            dataView: { show: true, readOnly: false },
            magicType: { show: true, type: ["line", "bar"] },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        legend: {
          data: ["节点数", "人数", "项目数"]
        },
        xAxis: [
          {
            type: "category",
            data: [
              "1月",
              "2月",
              "3月",
              "4月",
              "5月",
              "6月",
              "7月",
              "8月",
              "9月",
              "10月",
              "11月",
              "12月"
            ],
            axisPointer: {
              type: "shadow"
            }
          }
        ],
        yAxis: [
          {
            type: "value",
            name: "节点数量",
            min: 0,
            max: 250,
            interval: 50,
            axisLabel: {
              formatter: "{value} 个"
            }
          },
          {
            type: "value",
            name: "项目数",
            min: 0,
            max: 2500,
            interval: 500,
            axisLabel: {
              formatter: "{value} 个"
            }
          }
        ],
        series: [
          {
            name: "节点数量",
            type: "bar",
            data: [
              2.0,
              4.9,
              7.0,
              23.2,
              25.6,
              76.7,
              15.6,
              12.2,
              32.6,
              20.0,
              6.4,
              3.3
            ]
          },
          {
            name: "项目数",
            type: "bar",
            data: [
              2.6,
              5.9,
              9.0,
              26.4,
              28.7,
              70.7,
              15.6,
              12.2,
              48.7,
              18.8,
              6.0,
              2.3
            ]
          },
          {
            name: "人数",
            type: "line",
            yAxisIndex: 1,
            data: [
              2,
              3,
              3,
              4,
              6,
              10,
              20,
              20,
              20,
              16,
              12,
              20
            ]
          }
        ]
      });
    },

    drawLine2() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById("myChart2"));
      // 绘制图表
      myChart.setOption({
        angleAxis: {},
        radiusAxis: {
          type: "category",
          data: ["周一", "周二", "周三", "周四"],
          z: 10
        },
        polar: {},
        series: [
          {
            type: "bar",
            data: [1, 2, 3, 4],
            coordinateSystem: "polar",
            name: "A",
            stack: "a"
          },
          {
            type: "bar",
            data: [2, 4, 6, 8],
            coordinateSystem: "polar",
            name: "B",
            stack: "a"
          },
          {
            type: "bar",
            data: [1, 2, 3, 4],
            coordinateSystem: "polar",
            name: "C",
            stack: "a"
          }
        ],
        legend: {
          show: true,
          data: ["A", "B", "C"]
        }
      });
    }
  },
  mounted() {
    this.drawLine();
    this.drawLine2();
  }
};
</script>

<style>

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

.message {
  margin-top: 5px;
}
.card {
  height: 60px;
  weight: 40px;
}
.something i {
  font-size: 40px;
  position: absolute;
  right: 10px;
  top: 20px;
}
.something .s1 {
  font-size: 40px;
  font-weight: 700;
}
.something .s2 {
  /* margin-left: 10px; */
  display: block;
  color: #909399;
  font-size: 14px;
  margin-top: 5px;
}
.el-progress {
  margin-top: 10px;
  margin-left: 10px;
}
.dataChart {
  margin-top: 20px;
}
.elcard20 {
  height: 150px;
  /* line-height: 150px; */
  text-align: center;
  /* padding-bottom: 50px; */
   /* height: 100vh; */
  display: flex;
  justify-content: center;
  align-items: center;
}
.elcard20 span {
  font-size: 100px;
  margin-bottom: 50px;
}
.welcome {
  margin-top: 20px;
  height: 150px;
}
.span1 {
  margin-right: 50px;
  color: #e6a23c;
}
.span2 {
  margin-right: 50px;
  color: #67c23a;
}
.span3 {
  color: #409eff;
}

.elcard20 .text2 {
  background: url(https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1570863496&di=960d69c255174b9849963f7b79d4759e&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.qseeking.com%2Fuploads%2Fallimg%2F180622%2F1-1P6220Z63WY.jpg);
  background-size: contain;
  background-position: top left;
  -webkit-background-clip: text;
  color: transparent;
  font-size: 9rem;
  font-weight: bold;
  font-family: sans-serif;
}
</style>
