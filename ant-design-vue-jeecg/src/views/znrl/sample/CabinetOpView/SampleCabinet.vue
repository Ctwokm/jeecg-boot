<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="5" :sm="24">
            <a-form-item label="当前柜面">
              <a-select style="width: 100%" @change="changeCabinet" default-value="3-A%" >
                <a-select-option value="1-A%">
                  1号柜A面
                </a-select-option>
                <a-select-option value="1-B%">
                  1号柜B面
                </a-select-option>
                <a-select-option value="2-A%">
                  2号柜A面
                </a-select-option>
                <a-select-option value="2-B%">
                  2号柜B面
                </a-select-option>
                <a-select-option value="3-A%">
                  3号柜A面
                </a-select-option>
                <a-select-option value="3-B%">
                  3号柜B面
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="3" :sm="24">
            <a-input :disabled = "true" v-model="totalResult.totalCnt" addon-before="已存总数"  addon-after="瓶"/>
<!--            <a :disabled = "true" v-model="totalResult.totalCnt1" addon-before="已存总数"  addon-after="瓶"/>-->
          </a-col>
          <a-col :md="3" :sm="24">
            <a-input :disabled = "true" v-model="totalResult.leftCnt" addon-before="剩余总数" addon-after="瓶" />
          </a-col>
          <a-col :md="3" :sm="24">
            <a-input :disabled = "true" v-model="totalResult.expireCnt" addon-before="到期总数" addon-after="瓶" />
          </a-col>
          <a-col :md="3" :sm="24">
            <a-input :disabled = "true" v-model="totalResult.invalidCnt" addon-before="无效瓶数" addon-after="瓶" />
          </a-col>


          <span style="float: right;overflow: hidden;" class="table-page-search-submitButtons">
            <a-button type="primary" style="margin-left: 15px" @click="dealSample('1')" icon="search">取样</a-button>
            <a-button type="primary" style="margin-left: 15px" @click="dealSample('2')" icon="search">弃样</a-button>
          </span>
        </a-row>

      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
<!--      <a-button type="primary" icon="plus" @click="handleAdd">取样</a-button>-->
<!--      <a-button type="primary" icon="download" @click="handleExportXls('cabinet_op_rec_his')">弃样</a-button>-->
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button type="primary" icon="import">导入</a-button>-->
<!--      </a-upload>-->
<!--      &lt;!&ndash; 高级查询区域 &ndash;&gt;-->
<!--      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>-->
<!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
<!--        <a-menu slot="overlay">-->
<!--          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>-->
<!--        </a-menu>-->
<!--        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>-->
<!--      </a-dropdown>-->
    </div>

    <!-- table区域-begin -->
   <!-- <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>-->

    <table border="0" cellspacing="8" cellpadding="0" class="easy-table01">
      <!--<tr id="tr14">-->
      <!--<td @click="viewDetail('14-01');"><div><img id="img1401"/></div><div>C14-1</div></td>-->
      <!--<td @click="viewDetail('14-02');"><div><img id="img1402"/></div><div>C14-2</div></td>-->
      <!--<td @click="viewDetail('14-03');"><div><img id="img1403"/></div><div>C14-3</div></td>-->
      <!--<td @click="viewDetail('14-04');"><div><img id="img1404"/></div><div>C14-4</div></td>-->
      <!--<td @click="viewDetail('14-05');"><div><img id="img1405"/></div><div>C14-5</div></td>-->
      <!--<td @click="viewDetail('14-06');"><div><img id="img1406"/></div><div>C14-6</div></td>-->
      <!--<td @click="viewDetail('14-07');"><div><img id="img1407"/></div><div>C14-7</div></td>-->
      <!--<td @click="viewDetail('14-08');"><div><img id="img1408"/></div><div>C14-8</div></td>-->
      <!--<td @click="viewDetail('14-09');"><div><img id="img1409"/></div><div>C14-9</div></td>-->
      <!--<td @click="viewDetail('14-10');"><div><img id="img1410"/></div><div>C14-10</div></td>-->
      <!--<td @click="viewDetail('14-11');"><div><img id="img1411"/></div><div>C14-11</div></td>-->
      <!--<td @click="viewDetail('14-12');"><div><img id="img1412"/></div><div>C14-12</div></td>-->
      <!--<td @click="viewDetail('14-13');"><div><img id="img1413"/></div><div>C14-13</div></td>-->
      <!--<td @click="viewDetail('14-14');"><div><img id="img1414"/></div><div>C14-14</div></td>-->
      <!--<td @click="viewDetail('14-15');"><div><img id="img1415"/></div><div>C14-15</div></td>-->
      <!--<td @click="viewDetail('14-16');"><div><img id="img1416"/></div><div>C14-16</div></td>-->
      <!--<td @click="viewDetail('14-17');"><div><img id="img1417"/></div><div>C14-17</div></td>-->
      <!--<td @click="viewDetail('14-18');"><div><img id="img1418"/></div><div>C14-18</div></td>-->
      <!--</tr>-->
      <tr id="tr13">
        <td @click="viewDetail('13-01')"><div><img id="img1301"/></div><div>C13-1</div></td>
        <td @click="viewDetail('13-02')"><div><img id="img1302"/></div><div>C13-2</div></td>
        <td @click="viewDetail('13-03')"><div><img id="img1303"/></div><div>C13-3</div></td>
        <td @click="viewDetail('13-04')"><div><img id="img1304"/></div><div>C13-4</div></td>
        <td @click="viewDetail('13-05')"><div><img id="img1305"/></div><div>C13-5</div></td>
        <td @click="viewDetail('13-06')"><div><img id="img1306"/></div><div>C13-6</div></td>
        <td @click="viewDetail('13-07')"><div><img id="img1307"/></div><div>C13-7</div></td>
        <td @click="viewDetail('13-08')"><div><img id="img1308"/></div><div>C13-8</div></td>
        <td @click="viewDetail('13-09')"><div><img id="img1309"/></div><div>C13-9</div></td>
        <td @click="viewDetail('13-10')"><div><img id="img1310"/></div><div>C13-10</div></td>
        <td @click="viewDetail('13-11')"><div><img id="img1311"/></div><div>C13-11</div></td>
        <td @click="viewDetail('13-12')"><div><img id="img1312"/></div><div>C13-12</div></td>
        <td @click="viewDetail('13-13')"><div><img id="img1313"/></div><div>C13-13</div></td>
        <td @click="viewDetail('13-14')"><div><img id="img1314"/></div><div>C13-14</div></td>
        <td @click="viewDetail('13-15')"><div><img id="img1315"/></div><div>C13-15</div></td>
        <td @click="viewDetail('13-16')"><div><img id="img1316"/></div><div>C13-16</div></td>
        <td @click="viewDetail('13-17')"><div><img id="img1317"/></div><div>C13-17</div></td>
        <td @click="viewDetail('13-18')"><div><img id="img1318"/></div><div>C13-18</div></td>
        <td @click="viewDetail('13-19')"><div><img id="img1319"/></div><div>C13-19</div></td>
        <td @click="viewDetail('13-20')"><div><img id="img1320"/></div><div>C13-20</div></td>
        <td @click="viewDetail('13-21')"><div><img id="img1321"/></div><div>C13-21</div></td>
        <td @click="viewDetail('13-22')"><div><img id="img1322"/></div><div>C13-22</div></td>
        <td @click="viewDetail('13-23')"><div><img id="img1323"/></div><div>C13-23</div></td>
        <td @click="viewDetail('13-24')"><div><img id="img1324"/></div><div>C13-24</div></td>
      </tr>
      <tr id="tr12">
        <td id="td1201" @click="viewDetail('12-01')"><div><img id="img1201"/></div><div>C12-1</div></td>
        <td @click="viewDetail('12-02')"><div><img id="img1202"/></div><div>C12-2</div></td>
        <td @click="viewDetail('12-03')"><div><img id="img1203"/></div><div>C12-3</div></td>
        <td @click="viewDetail('12-04')"><div><img id="img1204"/></div><div>C12-4</div></td>
        <td @click="viewDetail('12-05')"><div><img id="img1205"/></div><div>C12-5</div></td>
        <td @click="viewDetail('12-06')"><div><img id="img1206"/></div><div>C12-6</div></td>
        <td @click="viewDetail('12-07')"><div><img id="img1207"/></div><div>C12-7</div></td>
        <td @click="viewDetail('12-08')"><div><img id="img1208"/></div><div>C12-8</div></td>
        <td @click="viewDetail('12-09')"><div><img id="img1209"/></div><div>C12-9</div></td>
        <td @click="viewDetail('12-10')"><div><img id="img1210"/></div><div>C12-10</div></td>
        <td @click="viewDetail('12-11')"><div><img id="img1211"/></div><div>C12-11</div></td>
        <td @click="viewDetail('12-12')"><div><img id="img1212"/></div><div>C12-12</div></td>
        <td @click="viewDetail('12-13')"><div><img id="img1213"/></div><div>C12-13</div></td>
        <td @click="viewDetail('12-14')"><div><img id="img1214"/></div><div>C12-14</div></td>
        <td @click="viewDetail('12-15')"><div><img id="img1215"/></div><div>C12-15</div></td>
        <td @click="viewDetail('12-16')"><div><img id="img1216"/></div><div>C12-16</div></td>
        <td @click="viewDetail('12-17')"><div><img id="img1217"/></div><div>C12-17</div></td>
        <td @click="viewDetail('12-18')"><div><img id="img1218"/></div><div>C12-18</div></td>
        <td @click="viewDetail('12-19')"><div><img id="img1219"/></div><div>C12-19</div></td>
        <td @click="viewDetail('12-20')"><div><img id="img1220"/></div><div>C12-20</div></td>
        <td @click="viewDetail('12-21')"><div><img id="img1221"/></div><div>C12-21</div></td>
        <td @click="viewDetail('12-22')"><div><img id="img1222"/></div><div>C12-22</div></td>
        <td @click="viewDetail('12-23')"><div><img id="img1223"/></div><div>C12-23</div></td>
        <td @click="viewDetail('12-24')"><div><img id="img1224"/></div><div>C12-24</div></td>
      </tr>
      <tr>
        <td id="td1101" @click="viewDetail('11-01')"><div><img id="img1101"/></div><div>C11-1</div></td>
        <td id="td1102" @click="viewDetail('11-02')"><div><img id="img1102"/></div><div>C11-2</div></td>
        <td id="td1103" @click="viewDetail('11-03')"><div><img id="img1103"/></div><div>C11-3</div></td>
        <td @click="viewDetail('11-04')"><div><img id="img1104"/></div><div>C11-4</div></td>
        <td @click="viewDetail('11-05')"><div><img id="img1105"/></div><div>C11-5</div></td>
        <td @click="viewDetail('11-06')"><div><img id="img1106"/></div><div>C11-6</div></td>
        <td @click="viewDetail('11-07')"><div><img id="img1107"/></div><div>C11-7</div></td>
        <td @click="viewDetail('11-08')"><div><img id="img1108"/></div><div>C11-8</div></td>
        <td @click="viewDetail('11-09')"><div><img id="img1109"/></div><div>C11-9</div></td>
        <td @click="viewDetail('11-10')"><div><img id="img1110"/></div><div>C11-10</div></td>
        <td @click="viewDetail('11-11')"><div><img id="img1111"/></div><div>C11-11</div></td>
        <td @click="viewDetail('11-12')"><div><img id="img1112"/></div><div>C11-12</div></td>
        <td @click="viewDetail('11-13')"><div><img id="img1113"/></div><div>C11-13</div></td>
        <td @click="viewDetail('11-14')"><div><img id="img1114"/></div><div>C11-14</div></td>
        <td @click="viewDetail('11-15')"><div><img id="img1115"/></div><div>C11-15</div></td>
        <td @click="viewDetail('11-16')"><div><img id="img1116"/></div><div>C11-16</div></td>
        <td @click="viewDetail('11-17')"><div><img id="img1117"/></div><div>C11-17</div></td>
        <td @click="viewDetail('11-18')"><div><img id="img1118"/></div><div>C11-18</div></td>
        <td @click="viewDetail('11-19')"><div><img id="img1119"/></div><div>C11-19</div></td>
        <td @click="viewDetail('11-20')"><div><img id="img1120"/></div><div>C11-20</div></td>
        <td @click="viewDetail('11-21')"><div><img id="img1121"/></div><div>C11-21</div></td>
        <td @click="viewDetail('11-22')"><div><img id="img1122"/></div><div>C11-22</div></td>
        <td @click="viewDetail('11-23')"><div><img id="img1123"/></div><div>C11-23</div></td>
        <td @click="viewDetail('11-24')"><div><img id="img1124"/></div><div>C11-24</div></td>
      </tr>
      <tr>
        <td id="td1001" @click="viewDetail('10-01')"><div><img id="img1001"/></div><div>C10-1</div></td>
        <td @click="viewDetail('10-02')"><div><img id="img1002"/></div><div>C10-2</div></td>
        <td @click="viewDetail('10-03')"><div><img id="img1003"/></div><div>C10-3</div></td>
        <td @click="viewDetail('10-04')"><div><img id="img1004"/></div><div>C10-4</div></td>
        <td @click="viewDetail('10-05')"><div><img id="img1005"/></div><div>C10-5</div></td>
        <td @click="viewDetail('10-06')"><div><img id="img1006"/></div><div>C10-6</div></td>
        <td @click="viewDetail('10-07')"><div><img id="img1007" :src="img1007"/></div><div>C10-7</div></td>
        <td @click="viewDetail('10-08')"><div><img id="img1008"/></div><div>C10-8</div></td>
        <td @click="viewDetail('10-09')"><div><img id="img1009"/></div><div>C10-9</div></td>
        <td @click="viewDetail('10-10')"><div><img id="img1010"/></div><div>C10-10</div></td>
        <td @click="viewDetail('10-11')"><div><img id="img1011"/></div><div>C10-11</div></td>
        <td @click="viewDetail('10-12')"><div><img id="img1012"/></div><div>C10-12</div></td>
        <td @click="viewDetail('10-13')"><div><img id="img1013"/></div><div>C10-13</div></td>
        <td @click="viewDetail('10-14')"><div><img id="img1014"/></div><div>C10-14</div></td>
        <td @click="viewDetail('10-15')"><div><img id="img1015"/></div><div>C10-15</div></td>
        <td @click="viewDetail('10-16')"><div><img id="img1016"/></div><div>C10-16</div></td>
        <td @click="viewDetail('10-17')"><div><img id="img1017"/></div><div>C10-17</div></td>
        <td @click="viewDetail('10-18')"><div><img id="img1018"/></div><div>C10-18</div></td>
        <td @click="viewDetail('10-19')"><div><img id="img1019"/></div><div>C10-19</div></td>
        <td @click="viewDetail('10-20')"><div><img id="img1020"/></div><div>C10-20</div></td>
        <td @click="viewDetail('10-21')"><div><img id="img1021"/></div><div>C10-21</div></td>
        <td @click="viewDetail('10-22')"><div><img id="img1022"/></div><div>C10-22</div></td>
        <td @click="viewDetail('10-23')"><div><img id="img1023"/></div><div>C10-23</div></td>
        <td @click="viewDetail('10-24')"><div><img id="img1024"/></div><div>C10-24</div></td>
      </tr>
      <tr>
        <td @click="viewDetail('09-01')"><div><img id="img0901"/></div><div>C9-1</div></td>
        <td @click="viewDetail('09-02')"><div><img id="img0902"/></div><div>C9-2</div></td>
        <td @click="viewDetail('09-03')"><div><img id="img0903"/></div><div>C9-3</div></td>
        <td @click="viewDetail('09-04')"><div><img id="img0904"/></div><div>C9-4</div></td>
        <td @click="viewDetail('09-05')"><div><img id="img0905"/></div><div>C9-5</div></td>
        <td @click="viewDetail('09-06')"><div><img id="img0906"/></div><div>C9-6</div></td>
        <td @click="viewDetail('09-07')"><div><img id="img0907"/></div><div>C9-7</div></td>
        <td @click="viewDetail('09-08')"><div><img id="img0908"/></div><div>C9-8</div></td>
        <td @click="viewDetail('09-09')"><div><img id="img0909"/></div><div>C9-9</div></td>
        <td @click="viewDetail('09-10')"><div><img id="img0910"/></div><div>C9-10</div></td>
        <td @click="viewDetail('09-11')"><div><img id="img0911"/></div><div>C9-11</div></td>
        <td @click="viewDetail('09-12')"><div><img id="img0912"/></div><div>C9-12</div></td>
        <td @click="viewDetail('09-13')"><div><img id="img0913"/></div><div>C9-13</div></td>
        <td @click="viewDetail('09-14')"><div><img id="img0914"/></div><div>C9-14</div></td>
        <td @click="viewDetail('09-15')"><div><img id="img0915"/></div><div>C9-15</div></td>
        <td @click="viewDetail('09-16')"><div><img id="img0916"/></div><div>C9-16</div></td>
        <td @click="viewDetail('09-17')"><div><img id="img0917"/></div><div>C9-17</div></td>
        <td @click="viewDetail('09-18')"><div><img id="img0918"/></div><div>C9-18</div></td>
        <td @click="viewDetail('09-19')"><div><img id="img0919"/></div><div>C9-19</div></td>
        <td @click="viewDetail('09-20')"><div><img id="img0920"/></div><div>C9-20</div></td>
        <td @click="viewDetail('09-21')"><div><img id="img0921"/></div><div>C9-21</div></td>
        <td @click="viewDetail('09-22')"><div><img id="img0922"/></div><div>C9-22</div></td>
        <td @click="viewDetail('09-23')"><div><img id="img0923"/></div><div>C9-23</div></td>
        <td @click="viewDetail('09-24')"><div><img id="img0924"/></div><div>C9-24</div></td>
      </tr>
      <tr>
        <td @click="viewDetail('08-01')"><div><img id="img0801"/></div><div>C8-1</div></td>
        <td @click="viewDetail('08-02')"><div><img id="img0802"/></div><div>C8-2</div></td>
        <td @click="viewDetail('08-03')"><div><img id="img0803"/></div><div>C8-3</div></td>
        <td @click="viewDetail('08-04')"><div><img id="img0804"/></div><div>C8-4</div></td>
        <td @click="viewDetail('08-05')"><div><img id="img0805"/></div><div>C8-5</div></td>
        <td @click="viewDetail('08-06')"><div><img id="img0806"/></div><div>C8-6</div></td>
        <td @click="viewDetail('08-07')"><div><img id="img0807"/></div><div>C8-7</div></td>
        <td @click="viewDetail('08-08')"><div><img id="img0808"/></div><div>C8-8</div></td>
        <td @click="viewDetail('08-09')"><div><img id="img0809"/></div><div>C8-9</div></td>
        <td @click="viewDetail('08-10')"><div><img id="img0810"/></div><div>C8-10</div></td>
        <td @click="viewDetail('08-11')"><div><img id="img0811"/></div><div>C8-11</div></td>
        <td @click="viewDetail('08-12')"><div><img id="img0812"/></div><div>C8-12</div></td>
        <td @click="viewDetail('08-13')"><div><img id="img0813"/></div><div>C8-13</div></td>
        <td @click="viewDetail('08-14')"><div><img id="img0814"/></div><div>C8-14</div></td>
        <td @click="viewDetail('08-15')"><div><img id="img0815"/></div><div>C8-15</div></td>
        <td @click="viewDetail('08-16')"><div><img id="img0816"/></div><div>C8-16</div></td>
        <td @click="viewDetail('08-17')"><div><img id="img0817"/></div><div>C8-17</div></td>
        <td @click="viewDetail('08-18')"><div><img id="img0818"/></div><div>C8-18</div></td>
        <td @click="viewDetail('08-19')"><div><img id="img0819"/></div><div>C8-19</div></td>
        <td @click="viewDetail('08-20')"><div><img id="img0820"/></div><div>C8-20</div></td>
        <td @click="viewDetail('08-21')"><div><img id="img0821"/></div><div>C8-21</div></td>
        <td @click="viewDetail('08-22')"><div><img id="img0822"/></div><div>C8-22</div></td>
        <td @click="viewDetail('08-23')"><div><img id="img0823"/></div><div>C8-23</div></td>
        <td @click="viewDetail('08-24')"><div><img id="img0824"/></div><div>C8-24</div></td>
      </tr>
      <tr>
        <td @click="viewDetail('07-01')"><div><img id="img0701"/></div><div>C7-1</div></td>
        <td @click="viewDetail('07-02')"><div><img id="img0702"/></div><div>C7-2</div></td>
        <td @click="viewDetail('07-03')"><div><img id="img0703"/></div><div>C7-3</div></td>
        <td @click="viewDetail('07-04')"><div><img id="img0704"/></div><div>C7-4</div></td>
        <td @click="viewDetail('07-05')"><div><img id="img0705"/></div><div>C7-5</div></td>
        <td @click="viewDetail('07-06')"><div><img id="img0706"/></div><div>C7-6</div></td>
        <td @click="viewDetail('07-07')"><div><img id="img0707"/></div><div>C7-7</div></td>
        <td @click="viewDetail('07-08')"><div><img id="img0708"/></div><div>C7-8</div></td>
        <td @click="viewDetail('07-09')"><div><img id="img0709"/></div><div>C7-9</div></td>
        <td @click="viewDetail('07-10')"><div><img id="img0710"/></div><div>C7-10</div></td>
        <td @click="viewDetail('07-11')"><div><img id="img0711"/></div><div>C7-11</div></td>
        <td @click="viewDetail('07-12')"><div><img id="img0712"/></div><div>C7-12</div></td>
        <td @click="viewDetail('07-13')"><div><img id="img0713"/></div><div>C7-13</div></td>
        <td @click="viewDetail('07-14')"><div><img id="img0714"/></div><div>C7-14</div></td>
        <td @click="viewDetail('07-15')"><div><img id="img0715"/></div><div>C7-15</div></td>
        <td @click="viewDetail('07-16')"><div><img id="img0716"/></div><div>C7-16</div></td>
        <td @click="viewDetail('07-17')"><div><img id="img0717"/></div><div>C7-17</div></td>
        <td @click="viewDetail('07-18')"><div><img id="img0718"/></div><div>C7-18</div></td>
        <td @click="viewDetail('07-19')"><div><img id="img0719"/></div><div>C7-19</div></td>
        <td @click="viewDetail('07-20')"><div><img id="img0720"/></div><div>C7-20</div></td>
        <td @click="viewDetail('07-21')"><div><img id="img0721"/></div><div>C7-21</div></td>
        <td @click="viewDetail('07-22')"><div><img id="img0722"/></div><div>C7-22</div></td>
        <td @click="viewDetail('07-23')"><div><img id="img0723"/></div><div>C7-23</div></td>
        <td @click="viewDetail('07-24')"><div><img id="img0724"/></div><div>C7-24</div></td>
      </tr>
      <tr>
        <td @click="viewDetail('06-01')"><div><img id="img0601"/></div><div>C6-1</div></td>
        <td @click="viewDetail('06-02')"><div><img id="img0602"/></div><div>C6-2</div></td>
        <td @click="viewDetail('06-03')"><div><img id="img0603"/></div><div>C6-3</div></td>
        <td @click="viewDetail('06-04')"><div><img id="img0604"/></div><div>C6-4</div></td>
        <td @click="viewDetail('06-05')"><div><img id="img0605"/></div><div>C6-5</div></td>
        <td @click="viewDetail('06-06')"><div><img id="img0606"/></div><div>C6-6</div></td>
        <td @click="viewDetail('06-07')"><div><img id="img0607"/></div><div>C6-7</div></td>
        <td @click="viewDetail('06-08')"><div><img id="img0608"/></div><div>C6-8</div></td>
        <td @click="viewDetail('06-09')"><div><img id="img0609"/></div><div>C6-9</div></td>
        <td @click="viewDetail('06-10')"><div><img id="img0610"/></div><div>C6-10</div></td>
        <td @click="viewDetail('06-11')"><div><img id="img0611"/></div><div>C6-11</div></td>
        <td @click="viewDetail('06-12')"><div><img id="img0612"/></div><div>C6-12</div></td>
        <td @click="viewDetail('06-13')"><div><img id="img0613"/></div><div>C6-13</div></td>
        <td @click="viewDetail('06-14')"><div><img id="img0614"/></div><div>C6-14</div></td>
        <td @click="viewDetail('06-15')"><div><img id="img0615"/></div><div>C6-15</div></td>
        <td @click="viewDetail('06-16')"><div><img id="img0616"/></div><div>C6-16</div></td>
        <td @click="viewDetail('06-17')"><div><img id="img0617"/></div><div>C6-17</div></td>
        <td @click="viewDetail('06-18')"><div><img id="img0618"/></div><div>C6-18</div></td>
        <td @click="viewDetail('06-19')"><div><img id="img0619"/></div><div>C6-19</div></td>
        <td @click="viewDetail('06-20')"><div><img id="img0620"/></div><div>C6-20</div></td>
        <td @click="viewDetail('06-21')"><div><img id="img0621"/></div><div>C6-21</div></td>
        <td @click="viewDetail('06-22')"><div><img id="img0622"/></div><div>C6-22</div></td>
        <td @click="viewDetail('06-23')"><div><img id="img0623"/></div><div>C6-23</div></td>
        <td @click="viewDetail('06-24')"><div><img id="img0624"/></div><div>C6-24</div></td>
      </tr>
      <tr>
        <td @click="viewDetail('05-01')"><div><img id="img0501"/></div><div>C5-1</div></td>
        <td @click="viewDetail('05-02')"><div><img id="img0502"/></div><div>C5-2</div></td>
        <td @click="viewDetail('05-03')"><div><img id="img0503"/></div><div>C5-3</div></td>
        <td @click="viewDetail('05-04')"><div><img id="img0504"/></div><div>C5-4</div></td>
        <td @click="viewDetail('05-05')"><div><img id="img0505"/></div><div>C5-5</div></td>
        <td @click="viewDetail('05-06')"><div><img id="img0506"/></div><div>C5-6</div></td>
        <td @click="viewDetail('05-07')"><div><img id="img0507"/></div><div>C5-7</div></td>
        <td @click="viewDetail('05-08')"><div><img id="img0508"/></div><div>C5-8</div></td>
        <td @click="viewDetail('05-09')"><div><img id="img0509"/></div><div>C5-9</div></td>
        <td @click="viewDetail('05-10')"><div><img id="img0510"/></div><div>C5-10</div></td>
        <td @click="viewDetail('05-11')"><div><img id="img0511"/></div><div>C5-11</div></td>
        <td @click="viewDetail('05-12')"><div><img id="img0512"/></div><div>C5-12</div></td>
        <td @click="viewDetail('05-13')"><div><img id="img0513"/></div><div>C5-13</div></td>
        <td @click="viewDetail('05-14')"><div><img id="img0514"/></div><div>C5-14</div></td>
        <td @click="viewDetail('05-15')"><div><img id="img0515"/></div><div>C5-15</div></td>
        <td @click="viewDetail('05-16')"><div><img id="img0516"/></div><div>C5-16</div></td>
        <td @click="viewDetail('05-17')"><div><img id="img0517"/></div><div>C5-17</div></td>
        <td @click="viewDetail('05-18')"><div><img id="img0518"/></div><div>C5-18</div></td>
        <td @click="viewDetail('05-19')"><div><img id="img0519"/></div><div>C5-19</div></td>
        <td @click="viewDetail('05-20')"><div><img id="img0520"/></div><div>C5-20</div></td>
        <td @click="viewDetail('05-21')"><div><img id="img0521"/></div><div>C5-21</div></td>
        <td @click="viewDetail('05-22')"><div><img id="img0522"/></div><div>C5-22</div></td>
        <td @click="viewDetail('05-23')"><div><img id="img0523"/></div><div>C5-23</div></td>
        <td @click="viewDetail('05-24')"><div><img id="img0524"/></div><div>C5-24</div></td>
      </tr>
      <tr>
        <td @click="viewDetail('04-01')"><div><img id="img0401"/></div><div>C4-1</div></td>
        <td @click="viewDetail('04-02')"><div><img id="img0402"/></div><div>C4-2</div></td>
        <td @click="viewDetail('04-03')"><div><img id="img0403"/></div><div>C4-3</div></td>
        <td @click="viewDetail('04-04')"><div><img id="img0404"/></div><div>C4-4</div></td>
        <td @click="viewDetail('04-05')"><div><img id="img0405"/></div><div>C4-5</div></td>
        <td @click="viewDetail('04-06')"><div><img id="img0406"/></div><div>C4-6</div></td>
        <td @click="viewDetail('04-07')"><div><img id="img0407"/></div><div>C4-7</div></td>
        <td @click="viewDetail('04-08')"><div><img id="img0408"/></div><div>C4-8</div></td>
        <td @click="viewDetail('04-09')"><div><img id="img0409"/></div><div>C4-9</div></td>
        <td @click="viewDetail('04-10')"><div><img id="img0410"/></div><div>C4-10</div></td>
        <td @click="viewDetail('04-11')"><div><img id="img0411"/></div><div>C4-11</div></td>
        <td @click="viewDetail('04-12')"><div><img id="img0412"/></div><div>C4-12</div></td>
        <td @click="viewDetail('04-13')"><div><img id="img0413"/></div><div>C4-13</div></td>
        <td @click="viewDetail('04-14')"><div><img id="img0414"/></div><div>C4-14</div></td>
        <td @click="viewDetail('04-15')"><div><img id="img0415"/></div><div>C4-15</div></td>
        <td @click="viewDetail('04-16')"><div><img id="img0416"/></div><div>C4-16</div></td>
        <td @click="viewDetail('04-17')"><div><img id="img0417"/></div><div>C4-17</div></td>
        <td @click="viewDetail('04-18')"><div><img id="img0418"/></div><div>C4-18</div></td>
        <td @click="viewDetail('04-19')"><div><img id="img0419"/></div><div>C4-19</div></td>
        <td @click="viewDetail('04-20')"><div><img id="img0420"/></div><div>C4-20</div></td>
        <td @click="viewDetail('04-21')"><div><img id="img0421"/></div><div>C4-21</div></td>
        <td @click="viewDetail('04-22')"><div><img id="img0422"/></div><div>C4-22</div></td>
        <td @click="viewDetail('04-23')"><div><img id="img0423"/></div><div>C4-23</div></td>
        <td @click="viewDetail('04-24')"><div><img id="img0424"/></div><div>C4-24</div></td>
      </tr>
      <tr>
        <td @click="viewDetail('03-01')"><div><img id="img0301"/></div><div>C3-1</div></td>
        <td @click="viewDetail('03-02')"><div><img id="img0302"/></div><div>C3-2</div></td>
        <td @click="viewDetail('03-03')"><div><img id="img0303"/></div><div>C3-3</div></td>
        <td @click="viewDetail('03-04')"><div><img id="img0304"/></div><div>C3-4</div></td>
        <td @click="viewDetail('03-05')"><div><img id="img0305"/></div><div>C3-5</div></td>
        <td @click="viewDetail('03-06')"><div><img id="img0306"/></div><div>C3-6</div></td>
        <td @click="viewDetail('03-07')"><div><img id="img0307"/></div><div>C3-7</div></td>
        <td @click="viewDetail('03-08')"><div><img id="img0308"/></div><div>C3-8</div></td>
        <td @click="viewDetail('03-09')"><div><img id="img0309"/></div><div>C3-9</div></td>
        <td @click="viewDetail('03-10')"><div><img id="img0310"/></div><div>C3-10</div></td>
        <td @click="viewDetail('03-11')"><div><img id="img0311"/></div><div>C3-11</div></td>
        <td @click="viewDetail('03-12')"><div><img id="img0312"/></div><div>C3-12</div></td>
        <td @click="viewDetail('03-13')"><div><img id="img0313"/></div><div>C3-13</div></td>
        <td @click="viewDetail('03-14')"><div><img id="img0314"/></div><div>C3-14</div></td>
        <td @click="viewDetail('03-15')"><div><img id="img0315"/></div><div>C3-15</div></td>
        <td @click="viewDetail('03-16')"><div><img id="img0316"/></div><div>C3-16</div></td>
        <td @click="viewDetail('03-17')"><div><img id="img0317"/></div><div>C3-17</div></td>
        <td @click="viewDetail('03-18')"><div><img id="img0318"/></div><div>C3-18</div></td>
        <td @click="viewDetail('03-19')"><div><img id="img0319"/></div><div>C3-19</div></td>
        <td @click="viewDetail('03-20')"><div><img id="img0320"/></div><div>C3-20</div></td>
        <td @click="viewDetail('03-21')"><div><img id="img0321"/></div><div>C3-21</div></td>
        <td @click="viewDetail('03-22')"><div><img id="img0322"/></div><div>C3-22</div></td>
        <td @click="viewDetail('03-23')"><div><img id="img0323"/></div><div>C3-23</div></td>
        <td @click="viewDetail('03-24')"><div><img id="img0324"/></div><div>C3-24</div></td>
      </tr>
      <tr>
        <td @click="viewDetail('02-01')"><div><img id="img0201"/></div><div>C2-1</div></td>
        <td @click="viewDetail('02-02')"><div><img id="img0202"/></div><div>C2-2</div></td>
        <td @click="viewDetail('02-03')"><div><img id="img0203"/></div><div>C2-3</div></td>
        <td @click="viewDetail('02-04')"><div><img id="img0204"/></div><div>C2-4</div></td>
        <td @click="viewDetail('02-05')"><div><img id="img0205"/></div><div>C2-5</div></td>
        <td @click="viewDetail('02-06')"><div><img id="img0206"/></div><div>C2-6</div></td>
        <td @click="viewDetail('02-07')"><div><img id="img0207"/></div><div>C2-7</div></td>
        <td @click="viewDetail('02-08')"><div><img id="img0208"/></div><div>C2-8</div></td>
        <td @click="viewDetail('02-09')"><div><img id="img0209"/></div><div>C2-9</div></td>
        <td @click="viewDetail('02-10')"><div><img id="img0210"/></div><div>C2-10</div></td>
        <td @click="viewDetail('02-11')"><div><img id="img0211"/></div><div>C2-11</div></td>
        <td @click="viewDetail('02-12')"><div><img id="img0212"/></div><div>C2-12</div></td>
        <td @click="viewDetail('02-13')"><div><img id="img0213"/></div><div>C2-13</div></td>
        <td @click="viewDetail('02-14')"><div><img id="img0214"/></div><div>C2-14</div></td>
        <td @click="viewDetail('02-15')"><div><img id="img0215"/></div><div>C2-15</div></td>
        <td @click="viewDetail('02-16')"><div><img id="img0216"/></div><div>C2-16</div></td>
        <td @click="viewDetail('02-17')"><div><img id="img0217"/></div><div>C2-17</div></td>
        <td @click="viewDetail('02-18')"><div><img id="img0218"/></div><div>C2-18</div></td>
        <td @click="viewDetail('02-19')"><div><img id="img0219"/></div><div>C2-19</div></td>
        <td @click="viewDetail('02-20')"><div><img id="img0220"/></div><div>C2-20</div></td>
        <td @click="viewDetail('02-21')"><div><img id="img0221"/></div><div>C2-21</div></td>
        <td @click="viewDetail('02-22')"><div><img id="img0222"/></div><div>C2-22</div></td>
        <td @click="viewDetail('02-23')"><div><img id="img0223"/></div><div>C2-23</div></td>
        <td @click="viewDetail('02-24')"><div><img id="img0224"/></div><div>C2-24</div></td>
      </tr>
      <tr>
        <td @click="viewDetail('01-01')"><div><img id="img0101"/></div><div>C1-1</div></td>
        <td @click="viewDetail('01-02')"><div><img id="img0102"/></div><div>C1-2</div></td>
        <td @click="viewDetail('01-03')"><div><img id="img0103"/></div><div>C1-3</div></td>
        <td @click="viewDetail('01-04')"><div><img id="img0104"/></div><div>C1-4</div></td>
        <td @click="viewDetail('01-05')"><div><img id="img0105"/></div><div>C1-5</div></td>
        <td @click="viewDetail('01-06')"><div><img id="img0106"/></div><div>C1-6</div></td>
        <td @click="viewDetail('01-07')"><div><img id="img0107"/></div><div>C1-7</div></td>
        <td @click="viewDetail('01-08')"><div><img id="img0108"/></div><div>C1-8</div></td>
        <td @click="viewDetail('01-09')"><div><img id="img0109"/></div><div>C1-9</div></td>
        <td @click="viewDetail('01-10')"><div><img id="img0110"/></div><div>C1-10</div></td>
        <td @click="viewDetail('01-11')"><div><img id="img0111"/></div><div>C1-11</div></td>
        <td @click="viewDetail('01-12')"><div><img id="img0112"/></div><div>C1-12</div></td>
        <td @click="viewDetail('01-13')"><div><img id="img0113"/></div><div>C1-13</div></td>
        <td @click="viewDetail('01-14')"><div><img id="img0114"/></div><div>C1-14</div></td>
        <td @click="viewDetail('01-15')"><div><img id="img0115"/></div><div>C1-15</div></td>
        <td @click="viewDetail('01-16')"><div><img id="img0116"/></div><div>C1-16</div></td>
        <td @click="viewDetail('01-17')"><div><img id="img0117"/></div><div>C1-17</div></td>
        <td @click="viewDetail('01-18')"><div><img id="img0118"/></div><div>C1-18</div></td>
        <td @click="viewDetail('01-19')"><div><img id="img0119"/></div><div>C1-19</div></td>
        <td @click="viewDetail('01-20')"><div><img id="img0120"/></div><div>C1-20</div></td>
        <td @click="viewDetail('01-21')"><div><img id="img0121"/></div><div>C1-21</div></td>
        <td @click="viewDetail('01-22')"><div><img id="img0122"/></div><div>C1-22</div></td>
        <td @click="viewDetail('01-23')"><div><img id="img0123"/></div><div>C1-23</div></td>
        <td @click="viewDetail('01-24')"><div><img id="img0124"/></div><div>C1-24</div></td>
      </tr>

    </table>
<!--    <cabinet-op-rec-his-modal ref="modalForm" @ok="modalFormOk"></cabinet-op-rec-his-modal>-->
    <SampleCabinetForm ref="modalForm" ></SampleCabinetForm>
    <SampleCabinetDetailForm ref="modalDetailForm" ></SampleCabinetDetailForm>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  //import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import SampleCabinetForm from './modules/SampleCabinetForm'
  import SampleCabinetDetailForm from './modules/SampleCabinetDetailForm'
  import moment from 'moment'
  import { httpAction, getAction, postAction} from '@/api/manage'
  import img_exist  from '@/assets/pm25/exist.png'
  import img_red  from '@/assets/pm25/red.png'
  import img_useless  from '@/assets/pm25/useless.png'
  import img_none  from '@/assets/pm25/none.png'


  export default {
    name: 'SampleCabinet',
    components: {
      SampleCabinetForm,
      SampleCabinetDetailForm
    },
    data () {
      return {
        description: 'cabinet_info管理页面',
        /* 排序参数 */
        isorter:{
          column: 'insert_time',
          order: 'desc',
        },
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'柜名',
            align:"center",
            dataIndex: 'ggName'
          },
          {
            title:'层名',
            align:"center",
            dataIndex: 'ccName'
          },

          {
            title:'位名',
            align:"center",
            dataIndex: 'wwName'
          },
          {
            title:'是否可用',
            align:"center",
            dataIndex: 'isEnabled',
            customRender:function (text) {
              return !text?"":(text=='1'?'是':'否')
            }
          },
          {
            title:'制样时间',
            align:"center",
            dataIndex: 'makeSampleTime'
          },
          {
            title:'存样时间',
            align:"center",
            dataIndex: 'depositTime'
          },
          {
            title:'煤样类型',
            align:"center",
            dataIndex: 'sampleType',
            customRender:function (text) {
              let st_type = '';
              switch (text){
                case '61':
                  st_type = '6mm全水样';
                  break;
                case '62':
                  st_type = '6mm存查样';
                  break;
                case '31':
                  st_type = '3mm化验样';
                  break;
                case '32':
                  st_type = '3mm存查样';
                  break;
                case '21':
                  st_type = '0.2mm化验样';
                  break;
                case '22':
                  st_type = '0.2mm存查样';
                  break;
              }
              return st_type;

            }
          },
          {
            title:'封装码',
            align:"center",
            dataIndex: 'packCode'
          },
          {
            title:'样瓶状态',
            align:"center",
            dataIndex: 'sampleStatus',
            customRender:function (text) {
              let st_type = '';
              switch (text){
                case '1':
                  st_type = '正常瓶';
                  break;
                case '2':
                  st_type = '超期瓶';
                  break;
                case '3':
                  st_type = '无效瓶';
                  break;
                case '4':
                  st_type = '无瓶';
                  break;
              }
              return st_type;

            }
          },
          {
            title:'操作人',
            align:"center",
            dataIndex: 'opName'
          }
        ],
        url: {
          // list: "/common/cabinetOpRecHis/list",
          //list: "/znrl/cabinet/qryCabinetInfoList",
          listlocal: "/znrl/cabinet/qryCabinetInfoList",

        },
        dictOptions:{},
        totalResult:{
          totalCnt:'',
          leftCnt:'',
          expireCnt:'',
          invalidCnt:'',

        },
        superFieldList:[],
        ggimgs: {},
        img1007:'',
        viewGG : {
          ggNo: '1',
          abBox: 'A',
        },
        queryParam: {
          ggName:'3',
          ccLikeName: 'A%',
        },
        queryData: [],

      }
    },
    created() {
    //this.getSuperFieldList();
    this.searchQuerylocal();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      moment,
      onChange(dates, dateStrings) {
        this.queryParam.depositStartTime = dateStrings[0]
        this.queryParam.depositEndTime = dateStrings[1];

      },
      changeCabinet(selectVal){
        var cabArr = selectVal.split("-");
        this.queryParam.ggName = cabArr[0];
        this.queryParam.ccLikeName = cabArr[1];

        this.viewGG.ggNo = cabArr[0];
        this.viewGG.abBox = cabArr[1].slice(0,-1);
        this.searchQuerylocal();
      },
      dealSample(dealType) {
        var dealName = dealType == "1" ? "取样" : "弃样";
        // dealTag = dealType;
        // qrySampleStatus = dealType == "1" ? "1" : "2";//暂时先认为弃样只对状态为2的
        this.$refs.modalForm.openSample(dealType);
        this.$refs.modalForm.title = dealName;
        // this.$refs.modalForm.disableSubmit = false;
      },

      viewDetail(no){
        //获取当前点击柜子的位置，组成 柜子X层X位，如：1-A-01-04
        var curCabinet = this.viewGG.ggNo +"-"+this.viewGG.abBox+ "-" + no;//1XA01X04

        // for(var i=0 ;i< this.queryData.length;i++){
        //   if(this.queryData[i]&&this.queryData[i].)
        // }
        //这些位置不需要
        if (curCabinet == "1-A-13-01"||curCabinet == "1-A-13-02"||curCabinet == "1-A-13-03"||curCabinet == "1-A-12-01"||
          curCabinet == "2-A-13-01"||curCabinet == "2-A-13-02"||curCabinet == "2-A-13-03"||curCabinet == "2-A-12-01") {
          return false;
        }
        this.$refs.modalDetailForm.openSample(curCabinet);
        this.$refs.modalDetailForm.title = '详细信息';

      },

      searchQuerylocal() {
        const that = this;

        //debugger;
        var gNo = this.viewGG.ggNo;
        var mianTag = this.viewGG.abBox;

        //特殊处理
        // if (gNo == "2") {
        //   if (mianTag == "A") {
        //     document.getElementById("tr11").style.display = "none";
        //     document.getElementById("tr12").style.display = "none";
        //     document.getElementById("tr13").style.display = "none";
        //     document.getElementById("tr14").style.display = "none";
        //   } else if (mianTag == "B") {
        //     document.getElementById("tr11").style.display = "";
        //     document.getElementById("tr12").style.display = "";
        //     document.getElementById("tr13").style.display = "none";
        //     document.getElementById("tr14").style.display = "none";
        //   }
        // } else {
        //   document.getElementById("tr11").style.display = "";
        //   document.getElementById("tr12").style.display = "";
        //   document.getElementById("tr13").style.display = "";
        //   document.getElementById("tr14").style.display = "";
        // }

        var totalStored = 0;
        var normal = 0;
        var left = 0;
        var expired = 0;
        var invalid = 0;
        var noBottle = 0;
        var val = {};
        getAction(this.url.listlocal, this.queryParam).then((res) => {

          console.log(res);
          if (res.success) {
            that.queryData = res.result;
            for (var a = 0; a < res.result.length; a++) {
              val = res.result[a];
              if (val.sampleStatus == '1'){//正常瓶
                normal++;
                if (that.isValidBox(val.idName, mianTag)) {
                   document.getElementById(val.idName).src=img_exist;
                }
              } else if(val.sampleStatus == '2') {//超期瓶
                expired++;
                if (that.isValidBox(val.idName, mianTag)) {
                  document.getElementById(val.idName).src = img_red;
                }
              } else if(val.sampleStatus == '3') {//无效瓶
                invalid++;
                if (that.isValidBox(val.idName, mianTag)) {
                  document.getElementById(val.idName).src = img_useless;
                   //this.img1007 =  require("@/assets/pm25/useless.png");
                }
              } else if(val.sampleStatus == '4') {//无瓶
                noBottle++;
                if (that.isValidBox(val.idName, mianTag)) {
                  document.getElementById(val.idName).src = img_none;
                  ////this.img1007  = require("@/assets/pm25/none.png");
                }
              } else {
                document.getElementById(val.idName).src = "";
              }
            }

            that.totalResult.totalCnt = normal+expired+invalid;
            that.totalResult.leftCnt = noBottle;
            that.totalResult.expireCnt = expired;
            that.totalResult.invalidCnt = invalid;

          } else {
            this.$message.warning(res.message)
          }
        })
      },
      //A面的13-1,2,3， 12-1是没有的，14行是空的 特殊处理每个地方不一样
       isValidBox(idName, mianTag){
        if (mianTag == "A") {
          //超过的层不接收
          if (parseInt(idName.substring(3,5)) > 12) {
            return false;
          }

          if (idName == "img1201") {
            return false;
          } else {
            return true;
          }
        } else {
          if (parseInt(idName.substring(3,5)) > 13) {
            return false;
          } else {
            return true;
          }

        }
      },
      getSuperFieldList(){
        let fieldList=[];
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
  .easy-table01{ background: #FFFFFF; border: 1px solid #d4d4d4; width: 100%; margin: 10px;}
  .easy-table01 td{ border: 1px solid #009900; color: #0099FF; text-align: center; padding: 5px; width:4%; height: 20px;}
</style>