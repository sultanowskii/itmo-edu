/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
$(document).ready(function() {

    $(".click-title").mouseenter( function(    e){
        e.preventDefault();
        this.style.cursor="pointer";
    });
    $(".click-title").mousedown( function(event){
        event.preventDefault();
    });

    // Ugly code while this script is shared among several pages
    try{
        refreshHitsPerSecond(true);
    } catch(e){}
    try{
        refreshResponseTimeOverTime(true);
    } catch(e){}
    try{
        refreshResponseTimePercentiles();
    } catch(e){}
});


var responseTimePercentilesInfos = {
        data: {"result": {"minY": 918.0, "minX": 0.0, "maxY": 13057.0, "series": [{"data": [[0.0, 918.0], [0.1, 918.0], [0.2, 938.0], [0.3, 938.0], [0.4, 949.0], [0.5, 949.0], [0.6, 964.0], [0.7, 964.0], [0.8, 979.0], [0.9, 979.0], [1.0, 994.0], [1.1, 994.0], [1.2, 1009.0], [1.3, 1024.0], [1.4, 1024.0], [1.5, 1039.0], [1.6, 1039.0], [1.7, 1054.0], [1.8, 1054.0], [1.9, 1069.0], [2.0, 1069.0], [2.1, 1085.0], [2.2, 1085.0], [2.3, 1100.0], [2.4, 1100.0], [2.5, 1115.0], [2.6, 1130.0], [2.7, 1130.0], [2.8, 1145.0], [2.9, 1145.0], [3.0, 1159.0], [3.1, 1159.0], [3.2, 1174.0], [3.3, 1174.0], [3.4, 1189.0], [3.5, 1189.0], [3.6, 1204.0], [3.7, 1219.0], [3.8, 1219.0], [3.9, 1233.0], [4.0, 1233.0], [4.1, 1248.0], [4.2, 1248.0], [4.3, 1263.0], [4.4, 1263.0], [4.5, 1278.0], [4.6, 1278.0], [4.7, 1294.0], [4.8, 1294.0], [4.9, 1309.0], [5.0, 1324.0], [5.1, 1324.0], [5.2, 1339.0], [5.3, 1339.0], [5.4, 1353.0], [5.5, 1353.0], [5.6, 1413.0], [5.7, 1413.0], [5.8, 1428.0], [5.9, 1428.0], [6.0, 1443.0], [6.1, 1451.0], [6.2, 1451.0], [6.3, 1466.0], [6.4, 1466.0], [6.5, 1481.0], [6.6, 1481.0], [6.7, 1496.0], [6.8, 1496.0], [6.9, 1511.0], [7.0, 1511.0], [7.1, 1526.0], [7.2, 1526.0], [7.3, 1540.0], [7.4, 1555.0], [7.5, 1555.0], [7.6, 1570.0], [7.7, 1570.0], [7.8, 1586.0], [7.9, 1586.0], [8.0, 1601.0], [8.1, 1601.0], [8.2, 1616.0], [8.3, 1616.0], [8.4, 1631.0], [8.5, 1631.0], [8.6, 1645.0], [8.7, 1660.0], [8.8, 1660.0], [8.9, 1675.0], [9.0, 1675.0], [9.1, 1690.0], [9.2, 1690.0], [9.3, 1704.0], [9.4, 1704.0], [9.5, 1719.0], [9.6, 1719.0], [9.7, 1734.0], [9.8, 1749.0], [9.9, 1749.0], [10.0, 1764.0], [10.1, 1764.0], [10.2, 1780.0], [10.3, 1780.0], [10.4, 1795.0], [10.5, 1795.0], [10.6, 1810.0], [10.7, 1810.0], [10.8, 1825.0], [10.9, 1825.0], [11.0, 1840.0], [11.1, 2504.0], [11.2, 2504.0], [11.3, 2519.0], [11.4, 2519.0], [11.5, 2535.0], [11.6, 2535.0], [11.7, 2549.0], [11.8, 2549.0], [11.9, 2564.0], [12.0, 2564.0], [12.1, 2580.0], [12.2, 2594.0], [12.3, 2594.0], [12.4, 2609.0], [12.5, 2609.0], [12.6, 2624.0], [12.7, 2624.0], [12.8, 2639.0], [12.9, 2639.0], [13.0, 2654.0], [13.1, 2654.0], [13.2, 2670.0], [13.3, 2670.0], [13.4, 2683.0], [13.5, 2698.0], [13.6, 2698.0], [13.7, 2712.0], [13.8, 2712.0], [13.9, 2727.0], [14.0, 2727.0], [14.1, 2742.0], [14.2, 2742.0], [14.3, 2757.0], [14.4, 2757.0], [14.5, 2772.0], [14.6, 2772.0], [14.7, 2787.0], [14.8, 2803.0], [14.9, 2803.0], [15.0, 2817.0], [15.1, 2817.0], [15.2, 2833.0], [15.3, 2833.0], [15.4, 2848.0], [15.5, 2848.0], [15.6, 2863.0], [15.7, 2863.0], [15.8, 2866.0], [15.9, 2866.0], [16.0, 2866.0], [16.1, 2866.0], [16.2, 2866.0], [16.3, 2866.0], [16.4, 2866.0], [16.5, 2868.0], [16.6, 2868.0], [16.7, 2868.0], [16.8, 2868.0], [16.9, 2868.0], [17.0, 2868.0], [17.1, 2868.0], [17.2, 2868.0], [17.3, 2868.0], [17.4, 2877.0], [17.5, 2877.0], [17.6, 2892.0], [17.7, 2892.0], [17.8, 2904.0], [17.9, 2904.0], [18.0, 2907.0], [18.1, 2907.0], [18.2, 2922.0], [18.3, 2937.0], [18.4, 2937.0], [18.5, 2952.0], [18.6, 2952.0], [18.7, 2967.0], [18.8, 2967.0], [18.9, 2982.0], [19.0, 2982.0], [19.1, 2996.0], [19.2, 2996.0], [19.3, 3012.0], [19.4, 3012.0], [19.5, 3026.0], [19.6, 3041.0], [19.7, 3041.0], [19.8, 3056.0], [19.9, 3056.0], [20.0, 3071.0], [20.1, 3071.0], [20.2, 3072.0], [20.3, 3072.0], [20.4, 3086.0], [20.5, 3086.0], [20.6, 3101.0], [20.7, 3101.0], [20.8, 3117.0], [20.9, 3125.0], [21.0, 3125.0], [21.1, 3131.0], [21.2, 3131.0], [21.3, 3146.0], [21.4, 3146.0], [21.5, 3162.0], [21.6, 3162.0], [21.7, 3176.0], [21.8, 3176.0], [21.9, 3187.0], [22.0, 3191.0], [22.1, 3191.0], [22.2, 3205.0], [22.3, 3205.0], [22.4, 3220.0], [22.5, 3220.0], [22.6, 3235.0], [22.7, 3235.0], [22.8, 3250.0], [22.9, 3250.0], [23.0, 3258.0], [23.1, 3258.0], [23.2, 3265.0], [23.3, 3280.0], [23.4, 3280.0], [23.5, 3295.0], [23.6, 3295.0], [23.7, 3311.0], [23.8, 3311.0], [23.9, 3315.0], [24.0, 3315.0], [24.1, 3326.0], [24.2, 3326.0], [24.3, 3340.0], [24.4, 3354.0], [24.5, 3354.0], [24.6, 3356.0], [24.7, 3356.0], [24.8, 3371.0], [24.9, 3371.0], [25.0, 3386.0], [25.1, 3386.0], [25.2, 3391.0], [25.3, 3391.0], [25.4, 3400.0], [25.5, 3400.0], [25.6, 3421.0], [25.7, 3463.0], [25.8, 3463.0], [25.9, 3493.0], [26.0, 3493.0], [26.1, 3515.0], [26.2, 3515.0], [26.3, 3566.0], [26.4, 3566.0], [26.5, 3576.0], [26.6, 3576.0], [26.7, 3639.0], [26.8, 3639.0], [26.9, 3653.0], [27.0, 3713.0], [27.1, 3713.0], [27.2, 3737.0], [27.3, 3737.0], [27.4, 3772.0], [27.5, 3772.0], [27.6, 3793.0], [27.7, 3793.0], [27.8, 3806.0], [27.9, 3806.0], [28.0, 3835.0], [28.1, 3846.0], [28.2, 3846.0], [28.3, 3865.0], [28.4, 3865.0], [28.5, 3865.0], [28.6, 3865.0], [28.7, 3866.0], [28.8, 3866.0], [28.9, 3866.0], [29.0, 3866.0], [29.1, 3866.0], [29.2, 3866.0], [29.3, 3866.0], [29.4, 3866.0], [29.5, 3866.0], [29.6, 3866.0], [29.7, 3866.0], [29.8, 3867.0], [29.9, 3867.0], [30.0, 3867.0], [30.1, 3867.0], [30.2, 3867.0], [30.3, 3867.0], [30.4, 3867.0], [30.5, 3877.0], [30.6, 3877.0], [30.7, 3888.0], [30.8, 3888.0], [30.9, 3915.0], [31.0, 3915.0], [31.1, 3932.0], [31.2, 3932.0], [31.3, 3961.0], [31.4, 3961.0], [31.5, 3993.0], [31.6, 3993.0], [31.7, 4015.0], [31.8, 4594.0], [31.9, 4594.0], [32.0, 4609.0], [32.1, 4609.0], [32.2, 4624.0], [32.3, 4624.0], [32.4, 4638.0], [32.5, 4638.0], [32.6, 4653.0], [32.7, 4653.0], [32.8, 4668.0], [32.9, 4668.0], [33.0, 4684.0], [33.1, 4698.0], [33.2, 4698.0], [33.3, 4714.0], [33.4, 4714.0], [33.5, 4727.0], [33.6, 4727.0], [33.7, 4744.0], [33.8, 4744.0], [33.9, 4758.0], [34.0, 4758.0], [34.1, 4774.0], [34.2, 4789.0], [34.3, 4789.0], [34.4, 4804.0], [34.5, 4804.0], [34.6, 4819.0], [34.7, 4819.0], [34.8, 4833.0], [34.9, 4833.0], [35.0, 4848.0], [35.1, 4848.0], [35.2, 4862.0], [35.3, 4862.0], [35.4, 4877.0], [35.5, 4892.0], [35.6, 4892.0], [35.7, 4907.0], [35.8, 4907.0], [35.9, 4923.0], [36.0, 4923.0], [36.1, 4937.0], [36.2, 4937.0], [36.3, 4954.0], [36.4, 4954.0], [36.5, 4969.0], [36.6, 4983.0], [36.7, 4983.0], [36.8, 4998.0], [36.9, 4998.0], [37.0, 5014.0], [37.1, 5014.0], [37.2, 5029.0], [37.3, 5029.0], [37.4, 5043.0], [37.5, 5043.0], [37.6, 5218.0], [37.7, 5218.0], [37.8, 5219.0], [37.9, 5219.0], [38.0, 5219.0], [38.1, 5219.0], [38.2, 5219.0], [38.3, 5219.0], [38.4, 5219.0], [38.5, 5219.0], [38.6, 5219.0], [38.7, 5220.0], [38.8, 5220.0], [38.9, 5220.0], [39.0, 5220.0], [39.1, 5220.0], [39.2, 5220.0], [39.3, 5220.0], [39.4, 5221.0], [39.5, 5221.0], [39.6, 5221.0], [39.7, 5221.0], [39.8, 5221.0], [39.9, 5221.0], [40.0, 5221.0], [40.1, 5221.0], [40.2, 5222.0], [40.3, 5222.0], [40.4, 5222.0], [40.5, 5222.0], [40.6, 5222.0], [40.7, 5222.0], [40.8, 5222.0], [40.9, 5222.0], [41.0, 5222.0], [41.1, 5222.0], [41.2, 5222.0], [41.3, 5222.0], [41.4, 5222.0], [41.5, 5222.0], [41.6, 5222.0], [41.7, 5222.0], [41.8, 5222.0], [41.9, 5222.0], [42.0, 5222.0], [42.1, 5222.0], [42.2, 5222.0], [42.3, 5222.0], [42.4, 5222.0], [42.5, 5222.0], [42.6, 5222.0], [42.7, 5222.0], [42.8, 5222.0], [42.9, 5222.0], [43.0, 5222.0], [43.1, 5223.0], [43.2, 5223.0], [43.3, 5447.0], [43.4, 5447.0], [43.5, 5462.0], [43.6, 5462.0], [43.7, 5475.0], [43.8, 5475.0], [43.9, 5493.0], [44.0, 5507.0], [44.1, 5507.0], [44.2, 5522.0], [44.3, 5522.0], [44.4, 5538.0], [44.5, 5538.0], [44.6, 5553.0], [44.7, 5553.0], [44.8, 5568.0], [44.9, 5568.0], [45.0, 5583.0], [45.1, 5583.0], [45.2, 5598.0], [45.3, 5612.0], [45.4, 5612.0], [45.5, 5627.0], [45.6, 5627.0], [45.7, 5642.0], [45.8, 5642.0], [45.9, 5657.0], [46.0, 5657.0], [46.1, 5671.0], [46.2, 5671.0], [46.3, 5686.0], [46.4, 5701.0], [46.5, 5701.0], [46.6, 5716.0], [46.7, 5716.0], [46.8, 5731.0], [46.9, 5731.0], [47.0, 5746.0], [47.1, 5746.0], [47.2, 5761.0], [47.3, 5761.0], [47.4, 5775.0], [47.5, 5775.0], [47.6, 5790.0], [47.7, 5805.0], [47.8, 5805.0], [47.9, 5820.0], [48.0, 5820.0], [48.1, 5835.0], [48.2, 5835.0], [48.3, 5851.0], [48.4, 5851.0], [48.5, 5865.0], [48.6, 5865.0], [48.7, 5881.0], [48.8, 5896.0], [48.9, 5896.0], [49.0, 5911.0], [49.1, 5911.0], [49.2, 5925.0], [49.3, 5925.0], [49.4, 5940.0], [49.5, 5940.0], [49.6, 5954.0], [49.7, 5954.0], [49.8, 5970.0], [49.9, 5970.0], [50.0, 5985.0], [50.1, 5999.0], [50.2, 5999.0], [50.3, 6014.0], [50.4, 6014.0], [50.5, 6029.0], [50.6, 6029.0], [50.7, 6044.0], [50.8, 6044.0], [50.9, 6059.0], [51.0, 6059.0], [51.1, 6074.0], [51.2, 6074.0], [51.3, 6089.0], [51.4, 6104.0], [51.5, 6104.0], [51.6, 6119.0], [51.7, 6119.0], [51.8, 6134.0], [51.9, 6134.0], [52.0, 6137.0], [52.1, 6137.0], [52.2, 6137.0], [52.3, 6137.0], [52.4, 6137.0], [52.5, 6139.0], [52.6, 6139.0], [52.7, 6139.0], [52.8, 6139.0], [52.9, 6139.0], [53.0, 6139.0], [53.1, 6140.0], [53.2, 6140.0], [53.3, 6140.0], [53.4, 6140.0], [53.5, 6140.0], [53.6, 6140.0], [53.7, 6140.0], [53.8, 6140.0], [53.9, 6140.0], [54.0, 6140.0], [54.1, 6140.0], [54.2, 6140.0], [54.3, 6140.0], [54.4, 6140.0], [54.5, 6140.0], [54.6, 6141.0], [54.7, 6141.0], [54.8, 6142.0], [54.9, 6142.0], [55.0, 6142.0], [55.1, 6145.0], [55.2, 6145.0], [55.3, 6145.0], [55.4, 6145.0], [55.5, 6145.0], [55.6, 6145.0], [55.7, 6145.0], [55.8, 6145.0], [55.9, 6145.0], [56.0, 6145.0], [56.1, 6146.0], [56.2, 6146.0], [56.3, 6146.0], [56.4, 6147.0], [56.5, 6147.0], [56.6, 6150.0], [56.7, 6150.0], [56.8, 6164.0], [56.9, 6164.0], [57.0, 6180.0], [57.1, 6180.0], [57.2, 6194.0], [57.3, 6194.0], [57.4, 6209.0], [57.5, 6223.0], [57.6, 6223.0], [57.7, 6238.0], [57.8, 6238.0], [57.9, 6254.0], [58.0, 6254.0], [58.1, 6268.0], [58.2, 6268.0], [58.3, 6283.0], [58.4, 6283.0], [58.5, 6298.0], [58.6, 6313.0], [58.7, 6313.0], [58.8, 6328.0], [58.9, 6328.0], [59.0, 6343.0], [59.1, 6343.0], [59.2, 6358.0], [59.3, 6358.0], [59.4, 6373.0], [59.5, 6373.0], [59.6, 6388.0], [59.7, 6388.0], [59.8, 6403.0], [59.9, 6418.0], [60.0, 6418.0], [60.1, 6432.0], [60.2, 6432.0], [60.3, 6447.0], [60.4, 6447.0], [60.5, 6462.0], [60.6, 6462.0], [60.7, 6478.0], [60.8, 6478.0], [60.9, 6492.0], [61.0, 6508.0], [61.1, 6508.0], [61.2, 6522.0], [61.3, 6522.0], [61.4, 6537.0], [61.5, 6537.0], [61.6, 6552.0], [61.7, 6552.0], [61.8, 6567.0], [61.9, 6567.0], [62.0, 6582.0], [62.1, 6582.0], [62.2, 6597.0], [62.3, 6612.0], [62.4, 6612.0], [62.5, 6627.0], [62.6, 6627.0], [62.7, 6640.0], [62.8, 6640.0], [62.9, 6655.0], [63.0, 6655.0], [63.1, 6678.0], [63.2, 6678.0], [63.3, 6679.0], [63.4, 6679.0], [63.5, 6680.0], [63.6, 6680.0], [63.7, 6680.0], [63.8, 6681.0], [63.9, 6681.0], [64.0, 6681.0], [64.1, 6681.0], [64.2, 6682.0], [64.3, 6682.0], [64.4, 6682.0], [64.5, 6682.0], [64.6, 6682.0], [64.7, 6682.0], [64.8, 6682.0], [64.9, 6682.0], [65.0, 6682.0], [65.1, 6682.0], [65.2, 6682.0], [65.3, 6682.0], [65.4, 6682.0], [65.5, 6682.0], [65.6, 6682.0], [65.7, 6682.0], [65.8, 6682.0], [65.9, 6682.0], [66.0, 6682.0], [66.1, 6682.0], [66.2, 6682.0], [66.3, 6682.0], [66.4, 6682.0], [66.5, 6682.0], [66.6, 6682.0], [66.7, 6682.0], [66.8, 6682.0], [66.9, 6682.0], [67.0, 6682.0], [67.1, 6683.0], [67.2, 6683.0], [67.3, 6683.0], [67.4, 6683.0], [67.5, 6683.0], [67.6, 6683.0], [67.7, 6683.0], [67.8, 6683.0], [67.9, 6683.0], [68.0, 6683.0], [68.1, 6683.0], [68.2, 6683.0], [68.3, 6683.0], [68.4, 6683.0], [68.5, 6683.0], [68.6, 6683.0], [68.7, 6683.0], [68.8, 6683.0], [68.9, 6683.0], [69.0, 6683.0], [69.1, 6683.0], [69.2, 6683.0], [69.3, 6683.0], [69.4, 6683.0], [69.5, 6683.0], [69.6, 6683.0], [69.7, 6683.0], [69.8, 6683.0], [69.9, 6683.0], [70.0, 6683.0], [70.1, 6683.0], [70.2, 6683.0], [70.3, 6683.0], [70.4, 6683.0], [70.5, 6683.0], [70.6, 6683.0], [70.7, 6683.0], [70.8, 6683.0], [70.9, 6683.0], [71.0, 6683.0], [71.1, 6683.0], [71.2, 6683.0], [71.3, 6683.0], [71.4, 6683.0], [71.5, 6683.0], [71.6, 6683.0], [71.7, 6683.0], [71.8, 6683.0], [71.9, 6683.0], [72.0, 6683.0], [72.1, 6683.0], [72.2, 6683.0], [72.3, 6683.0], [72.4, 6683.0], [72.5, 6683.0], [72.6, 6683.0], [72.7, 6683.0], [72.8, 6683.0], [72.9, 6683.0], [73.0, 6683.0], [73.1, 6684.0], [73.2, 6684.0], [73.3, 6684.0], [73.4, 6684.0], [73.5, 6684.0], [73.6, 6684.0], [73.7, 6684.0], [73.8, 6684.0], [73.9, 6684.0], [74.0, 6684.0], [74.1, 6684.0], [74.2, 6684.0], [74.3, 6684.0], [74.4, 6684.0], [74.5, 6684.0], [74.6, 6684.0], [74.7, 6685.0], [74.8, 6685.0], [74.9, 6685.0], [75.0, 6685.0], [75.1, 6685.0], [75.2, 6685.0], [75.3, 6685.0], [75.4, 6685.0], [75.5, 7037.0], [75.6, 7037.0], [75.7, 7048.0], [75.8, 7063.0], [75.9, 7063.0], [76.0, 7065.0], [76.1, 7065.0], [76.2, 7065.0], [76.3, 7065.0], [76.4, 7065.0], [76.5, 7065.0], [76.6, 7065.0], [76.7, 7065.0], [76.8, 7065.0], [76.9, 7065.0], [77.0, 7065.0], [77.1, 7065.0], [77.2, 7065.0], [77.3, 7065.0], [77.4, 7065.0], [77.5, 7065.0], [77.6, 7065.0], [77.7, 7066.0], [77.8, 7066.0], [77.9, 7066.0], [78.0, 7066.0], [78.1, 7066.0], [78.2, 7066.0], [78.3, 7066.0], [78.4, 7066.0], [78.5, 7066.0], [78.6, 7066.0], [78.7, 7066.0], [78.8, 7066.0], [78.9, 7066.0], [79.0, 7066.0], [79.1, 7066.0], [79.2, 7066.0], [79.3, 7066.0], [79.4, 7066.0], [79.5, 7066.0], [79.6, 7066.0], [79.7, 7066.0], [79.8, 7066.0], [79.9, 7066.0], [80.0, 7066.0], [80.1, 7067.0], [80.2, 7067.0], [80.3, 7068.0], [80.4, 7068.0], [80.5, 7068.0], [80.6, 7068.0], [80.7, 7068.0], [80.8, 7068.0], [80.9, 7068.0], [81.0, 7644.0], [81.1, 7644.0], [81.2, 7644.0], [81.3, 7644.0], [81.4, 7644.0], [81.5, 7644.0], [81.6, 7644.0], [81.7, 7644.0], [81.8, 7644.0], [81.9, 7645.0], [82.0, 7645.0], [82.1, 7645.0], [82.2, 7645.0], [82.3, 7645.0], [82.4, 7645.0], [82.5, 7645.0], [82.6, 7645.0], [82.7, 7645.0], [82.8, 7645.0], [82.9, 7645.0], [83.0, 7645.0], [83.1, 7645.0], [83.2, 7645.0], [83.3, 7645.0], [83.4, 7645.0], [83.5, 7645.0], [83.6, 7645.0], [83.7, 7645.0], [83.8, 7645.0], [83.9, 7645.0], [84.0, 7645.0], [84.1, 7645.0], [84.2, 7645.0], [84.3, 7646.0], [84.4, 7646.0], [84.5, 7646.0], [84.6, 7646.0], [84.7, 7646.0], [84.8, 7646.0], [84.9, 7946.0], [85.0, 7946.0], [85.1, 7960.0], [85.2, 7960.0], [85.3, 7975.0], [85.4, 7991.0], [85.5, 7991.0], [85.6, 8006.0], [85.7, 8006.0], [85.8, 8022.0], [85.9, 8022.0], [86.0, 8035.0], [86.1, 8035.0], [86.2, 8052.0], [86.3, 8052.0], [86.4, 8067.0], [86.5, 8067.0], [86.6, 8081.0], [86.7, 8097.0], [86.8, 8097.0], [86.9, 8112.0], [87.0, 8112.0], [87.1, 8127.0], [87.2, 8127.0], [87.3, 8142.0], [87.4, 8142.0], [87.5, 8156.0], [87.6, 8156.0], [87.7, 8171.0], [87.8, 8171.0], [87.9, 8185.0], [88.0, 8200.0], [88.1, 8200.0], [88.2, 8215.0], [88.3, 8215.0], [88.4, 8230.0], [88.5, 8230.0], [88.6, 8247.0], [88.7, 8247.0], [88.8, 8261.0], [88.9, 8261.0], [89.0, 8276.0], [89.1, 8291.0], [89.2, 8291.0], [89.3, 8306.0], [89.4, 8306.0], [89.5, 8322.0], [89.6, 8322.0], [89.7, 9000.0], [89.8, 9000.0], [89.9, 9000.0], [90.0, 9000.0], [90.1, 9000.0], [90.2, 9000.0], [90.3, 9000.0], [90.4, 9000.0], [90.5, 9000.0], [90.6, 9000.0], [90.7, 9000.0], [90.8, 9000.0], [90.9, 9000.0], [91.0, 9001.0], [91.1, 9001.0], [91.2, 9001.0], [91.3, 9001.0], [91.4, 9001.0], [91.5, 9037.0], [91.6, 9037.0], [91.7, 9037.0], [91.8, 9037.0], [91.9, 9037.0], [92.0, 9037.0], [92.1, 9037.0], [92.2, 9037.0], [92.3, 9038.0], [92.4, 9038.0], [92.5, 9038.0], [92.6, 9038.0], [92.7, 9038.0], [92.8, 9038.0], [92.9, 9038.0], [93.0, 9038.0], [93.1, 9038.0], [93.2, 9038.0], [93.3, 9038.0], [93.4, 9039.0], [93.5, 9039.0], [93.6, 9039.0], [93.7, 9039.0], [93.8, 9039.0], [93.9, 9039.0], [94.0, 9039.0], [94.1, 9040.0], [94.2, 9040.0], [94.3, 10716.0], [94.4, 10716.0], [94.5, 10729.0], [94.6, 10729.0], [94.7, 10744.0], [94.8, 10744.0], [94.9, 10759.0], [95.0, 10759.0], [95.1, 10774.0], [95.2, 10789.0], [95.3, 10789.0], [95.4, 10804.0], [95.5, 10804.0], [95.6, 12713.0], [95.7, 12713.0], [95.8, 12727.0], [95.9, 12727.0], [96.0, 12742.0], [96.1, 12742.0], [96.2, 12758.0], [96.3, 12758.0], [96.4, 12773.0], [96.5, 12787.0], [96.6, 12787.0], [96.7, 12801.0], [96.8, 12801.0], [96.9, 12816.0], [97.0, 12816.0], [97.1, 12831.0], [97.2, 12831.0], [97.3, 12847.0], [97.4, 12847.0], [97.5, 12861.0], [97.6, 12877.0], [97.7, 12877.0], [97.8, 12891.0], [97.9, 12891.0], [98.0, 12906.0], [98.1, 12906.0], [98.2, 12921.0], [98.3, 12921.0], [98.4, 12936.0], [98.5, 12936.0], [98.6, 12952.0], [98.7, 12952.0], [98.8, 12968.0], [98.9, 12981.0], [99.0, 12981.0], [99.1, 12997.0], [99.2, 12997.0], [99.3, 13012.0], [99.4, 13012.0], [99.5, 13027.0], [99.6, 13027.0], [99.7, 13041.0], [99.8, 13041.0], [99.9, 13057.0]], "isOverall": false, "label": "conf1 | request", "isController": false}], "supportsControllersDiscrimination": true, "maxX": 100.0, "title": "Response Time Percentiles"}},
        getOptions: function() {
            return {
                series: {
                    points: { show: false }
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimePercentiles'
                },
                xaxis: {
                    tickDecimals: 1,
                    axisLabel: "Percentiles",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Percentile value in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : %x.2 percentile was %y ms"
                },
                selection: { mode: "xy" },
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimePercentiles"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimesPercentiles"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimesPercentiles"), dataset, prepareOverviewOptions(options));
        }
};

/**
 * @param elementId Id of element where we display message
 */
function setEmptyGraph(elementId) {
    $(function() {
        $(elementId).text("No graph series with filter="+seriesFilter);
    });
}

// Response times percentiles
function refreshResponseTimePercentiles() {
    var infos = responseTimePercentilesInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimePercentiles");
        return;
    }
    if (isGraph($("#flotResponseTimesPercentiles"))){
        infos.createGraph();
    } else {
        var choiceContainer = $("#choicesResponseTimePercentiles");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimesPercentiles", "#overviewResponseTimesPercentiles");
        $('#bodyResponseTimePercentiles .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var responseTimeDistributionInfos = {
        data: {"result": {"minY": 1.0, "minX": 900.0, "maxY": 71.0, "series": [{"data": [[900.0, 6.0], [1000.0, 6.0], [1100.0, 7.0], [1200.0, 7.0], [1300.0, 4.0], [1400.0, 7.0], [1500.0, 6.0], [1600.0, 7.0], [1700.0, 7.0], [1800.0, 3.0], [2500.0, 7.0], [2600.0, 7.0], [2800.0, 16.0], [2700.0, 6.0], [2900.0, 8.0], [3000.0, 7.0], [3100.0, 9.0], [3300.0, 9.0], [3200.0, 8.0], [3400.0, 4.0], [3500.0, 3.0], [3600.0, 2.0], [3700.0, 4.0], [3800.0, 17.0], [3900.0, 4.0], [4000.0, 1.0], [4600.0, 7.0], [4500.0, 1.0], [4800.0, 7.0], [4700.0, 6.0], [4900.0, 7.0], [5000.0, 3.0], [5200.0, 31.0], [5600.0, 6.0], [5400.0, 4.0], [5500.0, 7.0], [5800.0, 7.0], [5700.0, 7.0], [6100.0, 32.0], [6000.0, 6.0], [5900.0, 7.0], [6300.0, 6.0], [6200.0, 7.0], [6600.0, 71.0], [6500.0, 7.0], [6400.0, 7.0], [7000.0, 30.0], [7600.0, 21.0], [7900.0, 4.0], [8100.0, 6.0], [8000.0, 7.0], [8200.0, 7.0], [8300.0, 2.0], [9000.0, 25.0], [10700.0, 6.0], [10800.0, 1.0], [12700.0, 6.0], [13000.0, 4.0], [12900.0, 7.0], [12800.0, 7.0]], "isOverall": false, "label": "conf1 | request", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 100, "maxX": 13000.0, "title": "Response Time Distribution"}},
        getOptions: function() {
            var granularity = this.data.result.granularity;
            return {
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimeDistribution'
                },
                xaxis:{
                    axisLabel: "Response times in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of responses",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                bars : {
                    show: true,
                    barWidth: this.data.result.granularity
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: function(label, xval, yval, flotItem){
                        return yval + " responses for " + label + " were between " + xval + " and " + (xval + granularity) + " ms";
                    }
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimeDistribution"), prepareData(data.result.series, $("#choicesResponseTimeDistribution")), options);
        }

};

// Response time distribution
function refreshResponseTimeDistribution() {
    var infos = responseTimeDistributionInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimeDistribution");
        return;
    }
    if (isGraph($("#flotResponseTimeDistribution"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimeDistribution");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        $('#footerResponseTimeDistribution .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};


var syntheticResponseTimeDistributionInfos = {
        data: {"result": {"minY": 37.0, "minX": 1.0, "ticks": [[0, "Requests having \nresponse time <= 500ms"], [1, "Requests having \nresponse time > 500ms and <= 1,500ms"], [2, "Requests having \nresponse time > 1,500ms"], [3, "Requests in error"]], "maxY": 442.0, "series": [{"data": [], "color": "#9ACD32", "isOverall": false, "label": "Requests having \nresponse time <= 500ms", "isController": false}, {"data": [[1.0, 37.0]], "color": "yellow", "isOverall": false, "label": "Requests having \nresponse time > 500ms and <= 1,500ms", "isController": false}, {"data": [[2.0, 442.0]], "color": "orange", "isOverall": false, "label": "Requests having \nresponse time > 1,500ms", "isController": false}, {"data": [[3.0, 62.0]], "color": "#FF6347", "isOverall": false, "label": "Requests in error", "isController": false}], "supportsControllersDiscrimination": false, "maxX": 3.0, "title": "Synthetic Response Times Distribution"}},
        getOptions: function() {
            return {
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendSyntheticResponseTimeDistribution'
                },
                xaxis:{
                    axisLabel: "Response times ranges",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                    tickLength:0,
                    min:-0.5,
                    max:3.5
                },
                yaxis: {
                    axisLabel: "Number of responses",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                bars : {
                    show: true,
                    align: "center",
                    barWidth: 0.25,
                    fill:.75
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: function(label, xval, yval, flotItem){
                        return yval + " " + label;
                    }
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var options = this.getOptions();
            prepareOptions(options, data);
            options.xaxis.ticks = data.result.ticks;
            $.plot($("#flotSyntheticResponseTimeDistribution"), prepareData(data.result.series, $("#choicesSyntheticResponseTimeDistribution")), options);
        }

};

// Response time distribution
function refreshSyntheticResponseTimeDistribution() {
    var infos = syntheticResponseTimeDistributionInfos;
    prepareSeries(infos.data, true);
    if (isGraph($("#flotSyntheticResponseTimeDistribution"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesSyntheticResponseTimeDistribution");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        $('#footerSyntheticResponseTimeDistribution .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var activeThreadsOverTimeInfos = {
        data: {"result": {"minY": 146.07578558225512, "minX": 1.74430992E12, "maxY": 146.07578558225512, "series": [{"data": [[1.74430992E12, 146.07578558225512]], "isOverall": false, "label": "conf1 | thread group", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.74430992E12, "title": "Active Threads Over Time"}},
        getOptions: function() {
            return {
                series: {
                    stack: true,
                    lines: {
                        show: true,
                        fill: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of active threads",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 6,
                    show: true,
                    container: '#legendActiveThreadsOverTime'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                selection: {
                    mode: 'xy'
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : At %x there were %y active threads"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesActiveThreadsOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotActiveThreadsOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewActiveThreadsOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Active Threads Over Time
function refreshActiveThreadsOverTime(fixTimestamps) {
    var infos = activeThreadsOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotActiveThreadsOverTime"))) {
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesActiveThreadsOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotActiveThreadsOverTime", "#overviewActiveThreadsOverTime");
        $('#footerActiveThreadsOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var timeVsThreadsInfos = {
        data: {"result": {"minY": 1144.137931034483, "minX": 1.0, "maxY": 13012.5, "series": [{"data": [[2.0, 3993.0], [3.0, 3961.0], [4.0, 3932.0], [5.0, 3915.0], [6.0, 3888.0], [7.0, 3877.0], [8.0, 3846.0], [9.0, 3835.0], [10.0, 3806.0], [11.0, 3793.0], [12.0, 3772.0], [13.0, 3737.0], [14.0, 3713.0], [15.0, 3653.0], [16.0, 3639.0], [17.0, 3576.0], [18.0, 3566.0], [19.0, 3515.0], [20.0, 3493.0], [21.0, 3463.0], [22.0, 3421.0], [23.0, 3391.0], [24.0, 3354.0], [25.0, 3315.0], [26.0, 3258.0], [27.0, 3187.0], [28.0, 3125.0], [29.0, 3072.0], [30.0, 2904.0], [41.0, 2868.0], [45.0, 2868.0], [48.0, 2868.0], [51.0, 5222.0], [55.0, 5221.8], [60.0, 5222.0], [62.0, 3070.0], [63.0, 5222.0], [67.0, 5221.25], [66.0, 5222.0], [64.0, 5221.0], [71.0, 2866.0], [70.0, 2866.0], [69.0, 5219.0], [75.0, 5219.0], [74.0, 5221.0], [73.0, 2866.0], [72.0, 2866.0], [77.0, 5219.0], [80.0, 7739.933333333333], [84.0, 3867.0], [92.0, 1144.137931034483], [94.0, 3866.0], [98.0, 3866.0], [101.0, 3866.0], [107.0, 7645.0], [105.0, 3866.0], [110.0, 7644.0], [113.0, 7266.950000000001], [116.0, 6141.666666666667], [127.0, 6502.125], [126.0, 6140.0], [135.0, 9038.0], [134.0, 6147.0], [133.0, 13012.5], [132.0, 12847.0], [131.0, 10876.0], [130.0, 12742.0], [128.0, 9038.75], [143.0, 6683.0], [141.0, 6683.0], [140.0, 6684.0], [139.0, 6684.0], [138.0, 9356.35294117647], [137.0, 11888.272727272726], [136.0, 9865.999999999998], [151.0, 6684.0], [150.0, 6684.0], [148.0, 6684.0], [146.0, 6684.0], [145.0, 6684.0], [144.0, 6683.0], [153.0, 1825.107142857143], [154.0, 2741.5], [159.0, 6682.8], [167.0, 6901.5675675675675], [165.0, 6681.0], [162.0, 6683.5], [161.0, 6683.0], [174.0, 4645.5], [172.0, 4609.0], [169.0, 7721.462686567165], [182.0, 4954.0], [179.0, 4744.0], [177.0, 4758.0], [176.0, 4676.0], [191.0, 4877.0], [190.0, 4779.6], [185.0, 4929.8], [200.0, 4740.802631578949], [1.0, 4015.0]], "isOverall": false, "label": "conf1 | request", "isController": false}, {"data": [[146.0739371534196, 5630.131238447312]], "isOverall": false, "label": "conf1 | request-Aggregated", "isController": false}], "supportsControllersDiscrimination": true, "maxX": 200.0, "title": "Time VS Threads"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    axisLabel: "Number of active threads",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response times in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: { noColumns: 2,show: true, container: '#legendTimeVsThreads' },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s: At %x.2 active threads, Average response time was %y.2 ms"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesTimeVsThreads"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotTimesVsThreads"), dataset, options);
            // setup overview
            $.plot($("#overviewTimesVsThreads"), dataset, prepareOverviewOptions(options));
        }
};

// Time vs threads
function refreshTimeVsThreads(){
    var infos = timeVsThreadsInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyTimeVsThreads");
        return;
    }
    if(isGraph($("#flotTimesVsThreads"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTimeVsThreads");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTimesVsThreads", "#overviewTimesVsThreads");
        $('#footerTimeVsThreads .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var bytesThroughputOverTimeInfos = {
        data : {"result": {"minY": 1406.6, "minX": 1.74430992E12, "maxY": 2084.9166666666665, "series": [{"data": [[1.74430992E12, 2084.9166666666665]], "isOverall": false, "label": "Bytes received per second", "isController": false}, {"data": [[1.74430992E12, 1406.6]], "isOverall": false, "label": "Bytes sent per second", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.74430992E12, "title": "Bytes Throughput Over Time"}},
        getOptions : function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity) ,
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Bytes / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendBytesThroughputOverTime'
                },
                selection: {
                    mode: "xy"
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y"
                }
            };
        },
        createGraph : function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesBytesThroughputOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotBytesThroughputOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewBytesThroughputOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Bytes throughput Over Time
function refreshBytesThroughputOverTime(fixTimestamps) {
    var infos = bytesThroughputOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotBytesThroughputOverTime"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesBytesThroughputOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotBytesThroughputOverTime", "#overviewBytesThroughputOverTime");
        $('#footerBytesThroughputOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var responseTimesOverTimeInfos = {
        data: {"result": {"minY": 5630.131238447312, "minX": 1.74430992E12, "maxY": 5630.131238447312, "series": [{"data": [[1.74430992E12, 5630.131238447312]], "isOverall": false, "label": "conf1 | request", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.74430992E12, "title": "Response Time Over Time"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average response time was %y ms"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Response Times Over Time
function refreshResponseTimeOverTime(fixTimestamps) {
    var infos = responseTimesOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimeOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotResponseTimesOverTime"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimesOverTime", "#overviewResponseTimesOverTime");
        $('#footerResponseTimesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var latenciesOverTimeInfos = {
        data: {"result": {"minY": 5630.120147874308, "minX": 1.74430992E12, "maxY": 5630.120147874308, "series": [{"data": [[1.74430992E12, 5630.120147874308]], "isOverall": false, "label": "conf1 | request", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.74430992E12, "title": "Latencies Over Time"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response latencies in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendLatenciesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average latency was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesLatenciesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotLatenciesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewLatenciesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Latencies Over Time
function refreshLatenciesOverTime(fixTimestamps) {
    var infos = latenciesOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyLatenciesOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotLatenciesOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesLatenciesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotLatenciesOverTime", "#overviewLatenciesOverTime");
        $('#footerLatenciesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var connectTimeOverTimeInfos = {
        data: {"result": {"minY": 0.10536044362292066, "minX": 1.74430992E12, "maxY": 0.10536044362292066, "series": [{"data": [[1.74430992E12, 0.10536044362292066]], "isOverall": false, "label": "conf1 | request", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.74430992E12, "title": "Connect Time Over Time"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getConnectTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average Connect Time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendConnectTimeOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average connect time was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesConnectTimeOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotConnectTimeOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewConnectTimeOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Connect Time Over Time
function refreshConnectTimeOverTime(fixTimestamps) {
    var infos = connectTimeOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyConnectTimeOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotConnectTimeOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesConnectTimeOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotConnectTimeOverTime", "#overviewConnectTimeOverTime");
        $('#footerConnectTimeOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var responseTimePercentilesOverTimeInfos = {
        data: {"result": {"minY": 918.0, "minX": 1.74430992E12, "maxY": 13057.0, "series": [{"data": [[1.74430992E12, 13057.0]], "isOverall": false, "label": "Max", "isController": false}, {"data": [[1.74430992E12, 918.0]], "isOverall": false, "label": "Min", "isController": false}, {"data": [[1.74430992E12, 9001.0]], "isOverall": false, "label": "90th percentile", "isController": false}, {"data": [[1.74430992E12, 13000.0]], "isOverall": false, "label": "99th percentile", "isController": false}, {"data": [[1.74430992E12, 5985.0]], "isOverall": false, "label": "Median", "isController": false}, {"data": [[1.74430992E12, 12713.0]], "isOverall": false, "label": "95th percentile", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.74430992E12, "title": "Response Time Percentiles Over Time (successful requests only)"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true,
                        fill: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Response Time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimePercentilesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Response time was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimePercentilesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimePercentilesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimePercentilesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Response Time Percentiles Over Time
function refreshResponseTimePercentilesOverTime(fixTimestamps) {
    var infos = responseTimePercentilesOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotResponseTimePercentilesOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesResponseTimePercentilesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimePercentilesOverTime", "#overviewResponseTimePercentilesOverTime");
        $('#footerResponseTimePercentilesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};


var responseTimeVsRequestInfos = {
    data: {"result": {"minY": 1383.0, "minX": 27.0, "maxY": 9038.0, "series": [{"data": [[64.0, 9038.0], [33.0, 7644.0], [74.0, 6683.0], [53.0, 5222.0], [27.0, 3713.0], [113.0, 6051.5], [56.0, 7068.0], [30.0, 1383.0], [61.0, 2952.0]], "isOverall": false, "label": "Successes", "isController": false}, {"data": [[74.0, 6683.0], [113.0, 4819.0]], "isOverall": false, "label": "Failures", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 1000, "maxX": 113.0, "title": "Response Time Vs Request"}},
    getOptions: function() {
        return {
            series: {
                lines: {
                    show: false
                },
                points: {
                    show: true
                }
            },
            xaxis: {
                axisLabel: "Global number of requests per second",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            yaxis: {
                axisLabel: "Median Response Time in ms",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            legend: {
                noColumns: 2,
                show: true,
                container: '#legendResponseTimeVsRequest'
            },
            selection: {
                mode: 'xy'
            },
            grid: {
                hoverable: true // IMPORTANT! this is needed for tooltip to work
            },
            tooltip: true,
            tooltipOpts: {
                content: "%s : Median response time at %x req/s was %y ms"
            },
            colors: ["#9ACD32", "#FF6347"]
        };
    },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesResponseTimeVsRequest"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotResponseTimeVsRequest"), dataset, options);
        // setup overview
        $.plot($("#overviewResponseTimeVsRequest"), dataset, prepareOverviewOptions(options));

    }
};

// Response Time vs Request
function refreshResponseTimeVsRequest() {
    var infos = responseTimeVsRequestInfos;
    prepareSeries(infos.data);
    if (isGraph($("#flotResponseTimeVsRequest"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimeVsRequest");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimeVsRequest", "#overviewResponseTimeVsRequest");
        $('#footerResponseRimeVsRequest .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};


var latenciesVsRequestInfos = {
    data: {"result": {"minY": 1383.0, "minX": 27.0, "maxY": 9038.0, "series": [{"data": [[64.0, 9038.0], [33.0, 7644.0], [74.0, 6683.0], [53.0, 5222.0], [27.0, 3713.0], [113.0, 6051.5], [56.0, 7068.0], [30.0, 1383.0], [61.0, 2952.0]], "isOverall": false, "label": "Successes", "isController": false}, {"data": [[74.0, 6683.0], [113.0, 4819.0]], "isOverall": false, "label": "Failures", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 1000, "maxX": 113.0, "title": "Latencies Vs Request"}},
    getOptions: function() {
        return{
            series: {
                lines: {
                    show: false
                },
                points: {
                    show: true
                }
            },
            xaxis: {
                axisLabel: "Global number of requests per second",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            yaxis: {
                axisLabel: "Median Latency in ms",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            legend: { noColumns: 2,show: true, container: '#legendLatencyVsRequest' },
            selection: {
                mode: 'xy'
            },
            grid: {
                hoverable: true // IMPORTANT! this is needed for tooltip to work
            },
            tooltip: true,
            tooltipOpts: {
                content: "%s : Median Latency time at %x req/s was %y ms"
            },
            colors: ["#9ACD32", "#FF6347"]
        };
    },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesLatencyVsRequest"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotLatenciesVsRequest"), dataset, options);
        // setup overview
        $.plot($("#overviewLatenciesVsRequest"), dataset, prepareOverviewOptions(options));
    }
};

// Latencies vs Request
function refreshLatenciesVsRequest() {
        var infos = latenciesVsRequestInfos;
        prepareSeries(infos.data);
        if(isGraph($("#flotLatenciesVsRequest"))){
            infos.createGraph();
        }else{
            var choiceContainer = $("#choicesLatencyVsRequest");
            createLegend(choiceContainer, infos);
            infos.createGraph();
            setGraphZoomable("#flotLatenciesVsRequest", "#overviewLatenciesVsRequest");
            $('#footerLatenciesVsRequest .legendColorBox > div').each(function(i){
                $(this).clone().prependTo(choiceContainer.find("li").eq(i));
            });
        }
};

var hitsPerSecondInfos = {
        data: {"result": {"minY": 9.016666666666667, "minX": 1.74430992E12, "maxY": 9.016666666666667, "series": [{"data": [[1.74430992E12, 9.016666666666667]], "isOverall": false, "label": "hitsPerSecond", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.74430992E12, "title": "Hits Per Second"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of hits / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendHitsPerSecond"
                },
                selection: {
                    mode : 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y.2 hits/sec"
                }
            };
        },
        createGraph: function createGraph() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesHitsPerSecond"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotHitsPerSecond"), dataset, options);
            // setup overview
            $.plot($("#overviewHitsPerSecond"), dataset, prepareOverviewOptions(options));
        }
};

// Hits per second
function refreshHitsPerSecond(fixTimestamps) {
    var infos = hitsPerSecondInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if (isGraph($("#flotHitsPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesHitsPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotHitsPerSecond", "#overviewHitsPerSecond");
        $('#footerHitsPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var codesPerSecondInfos = {
        data: {"result": {"minY": 1.0333333333333334, "minX": 1.74430992E12, "maxY": 7.983333333333333, "series": [{"data": [[1.74430992E12, 7.983333333333333]], "isOverall": false, "label": "200", "isController": false}, {"data": [[1.74430992E12, 1.0333333333333334]], "isOverall": false, "label": "500", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.74430992E12, "title": "Codes Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of responses / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendCodesPerSecond"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "Number of Response Codes %s at %x was %y.2 responses / sec"
                }
            };
        },
    createGraph: function() {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesCodesPerSecond"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotCodesPerSecond"), dataset, options);
        // setup overview
        $.plot($("#overviewCodesPerSecond"), dataset, prepareOverviewOptions(options));
    }
};

// Codes per second
function refreshCodesPerSecond(fixTimestamps) {
    var infos = codesPerSecondInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotCodesPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesCodesPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotCodesPerSecond", "#overviewCodesPerSecond");
        $('#footerCodesPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var transactionsPerSecondInfos = {
        data: {"result": {"minY": 1.0333333333333334, "minX": 1.74430992E12, "maxY": 7.983333333333333, "series": [{"data": [[1.74430992E12, 7.983333333333333]], "isOverall": false, "label": "conf1 | request-success", "isController": false}, {"data": [[1.74430992E12, 1.0333333333333334]], "isOverall": false, "label": "conf1 | request-failure", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.74430992E12, "title": "Transactions Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of transactions / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendTransactionsPerSecond"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y transactions / sec"
                }
            };
        },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesTransactionsPerSecond"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotTransactionsPerSecond"), dataset, options);
        // setup overview
        $.plot($("#overviewTransactionsPerSecond"), dataset, prepareOverviewOptions(options));
    }
};

// Transactions per second
function refreshTransactionsPerSecond(fixTimestamps) {
    var infos = transactionsPerSecondInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyTransactionsPerSecond");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotTransactionsPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTransactionsPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTransactionsPerSecond", "#overviewTransactionsPerSecond");
        $('#footerTransactionsPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var totalTPSInfos = {
        data: {"result": {"minY": 1.0333333333333334, "minX": 1.74430992E12, "maxY": 7.983333333333333, "series": [{"data": [[1.74430992E12, 7.983333333333333]], "isOverall": false, "label": "Transaction-success", "isController": false}, {"data": [[1.74430992E12, 1.0333333333333334]], "isOverall": false, "label": "Transaction-failure", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.74430992E12, "title": "Total Transactions Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of transactions / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendTotalTPS"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y transactions / sec"
                },
                colors: ["#9ACD32", "#FF6347"]
            };
        },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesTotalTPS"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotTotalTPS"), dataset, options);
        // setup overview
        $.plot($("#overviewTotalTPS"), dataset, prepareOverviewOptions(options));
    }
};

// Total Transactions per second
function refreshTotalTPS(fixTimestamps) {
    var infos = totalTPSInfos;
    // We want to ignore seriesFilter
    prepareSeries(infos.data, false, true);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotTotalTPS"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTotalTPS");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTotalTPS", "#overviewTotalTPS");
        $('#footerTotalTPS .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

// Collapse the graph matching the specified DOM element depending the collapsed
// status
function collapse(elem, collapsed){
    if(collapsed){
        $(elem).parent().find(".fa-chevron-up").removeClass("fa-chevron-up").addClass("fa-chevron-down");
    } else {
        $(elem).parent().find(".fa-chevron-down").removeClass("fa-chevron-down").addClass("fa-chevron-up");
        if (elem.id == "bodyBytesThroughputOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshBytesThroughputOverTime(true);
            }
            document.location.href="#bytesThroughputOverTime";
        } else if (elem.id == "bodyLatenciesOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshLatenciesOverTime(true);
            }
            document.location.href="#latenciesOverTime";
        } else if (elem.id == "bodyCustomGraph") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshCustomGraph(true);
            }
            document.location.href="#responseCustomGraph";
        } else if (elem.id == "bodyConnectTimeOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshConnectTimeOverTime(true);
            }
            document.location.href="#connectTimeOverTime";
        } else if (elem.id == "bodyResponseTimePercentilesOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimePercentilesOverTime(true);
            }
            document.location.href="#responseTimePercentilesOverTime";
        } else if (elem.id == "bodyResponseTimeDistribution") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimeDistribution();
            }
            document.location.href="#responseTimeDistribution" ;
        } else if (elem.id == "bodySyntheticResponseTimeDistribution") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshSyntheticResponseTimeDistribution();
            }
            document.location.href="#syntheticResponseTimeDistribution" ;
        } else if (elem.id == "bodyActiveThreadsOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshActiveThreadsOverTime(true);
            }
            document.location.href="#activeThreadsOverTime";
        } else if (elem.id == "bodyTimeVsThreads") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTimeVsThreads();
            }
            document.location.href="#timeVsThreads" ;
        } else if (elem.id == "bodyCodesPerSecond") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshCodesPerSecond(true);
            }
            document.location.href="#codesPerSecond";
        } else if (elem.id == "bodyTransactionsPerSecond") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTransactionsPerSecond(true);
            }
            document.location.href="#transactionsPerSecond";
        } else if (elem.id == "bodyTotalTPS") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTotalTPS(true);
            }
            document.location.href="#totalTPS";
        } else if (elem.id == "bodyResponseTimeVsRequest") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimeVsRequest();
            }
            document.location.href="#responseTimeVsRequest";
        } else if (elem.id == "bodyLatenciesVsRequest") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshLatenciesVsRequest();
            }
            document.location.href="#latencyVsRequest";
        }
    }
}

/*
 * Activates or deactivates all series of the specified graph (represented by id parameter)
 * depending on checked argument.
 */
function toggleAll(id, checked){
    var placeholder = document.getElementById(id);

    var cases = $(placeholder).find(':checkbox');
    cases.prop('checked', checked);
    $(cases).parent().children().children().toggleClass("legend-disabled", !checked);

    var choiceContainer;
    if ( id == "choicesBytesThroughputOverTime"){
        choiceContainer = $("#choicesBytesThroughputOverTime");
        refreshBytesThroughputOverTime(false);
    } else if(id == "choicesResponseTimesOverTime"){
        choiceContainer = $("#choicesResponseTimesOverTime");
        refreshResponseTimeOverTime(false);
    }else if(id == "choicesResponseCustomGraph"){
        choiceContainer = $("#choicesResponseCustomGraph");
        refreshCustomGraph(false);
    } else if ( id == "choicesLatenciesOverTime"){
        choiceContainer = $("#choicesLatenciesOverTime");
        refreshLatenciesOverTime(false);
    } else if ( id == "choicesConnectTimeOverTime"){
        choiceContainer = $("#choicesConnectTimeOverTime");
        refreshConnectTimeOverTime(false);
    } else if ( id == "choicesResponseTimePercentilesOverTime"){
        choiceContainer = $("#choicesResponseTimePercentilesOverTime");
        refreshResponseTimePercentilesOverTime(false);
    } else if ( id == "choicesResponseTimePercentiles"){
        choiceContainer = $("#choicesResponseTimePercentiles");
        refreshResponseTimePercentiles();
    } else if(id == "choicesActiveThreadsOverTime"){
        choiceContainer = $("#choicesActiveThreadsOverTime");
        refreshActiveThreadsOverTime(false);
    } else if ( id == "choicesTimeVsThreads"){
        choiceContainer = $("#choicesTimeVsThreads");
        refreshTimeVsThreads();
    } else if ( id == "choicesSyntheticResponseTimeDistribution"){
        choiceContainer = $("#choicesSyntheticResponseTimeDistribution");
        refreshSyntheticResponseTimeDistribution();
    } else if ( id == "choicesResponseTimeDistribution"){
        choiceContainer = $("#choicesResponseTimeDistribution");
        refreshResponseTimeDistribution();
    } else if ( id == "choicesHitsPerSecond"){
        choiceContainer = $("#choicesHitsPerSecond");
        refreshHitsPerSecond(false);
    } else if(id == "choicesCodesPerSecond"){
        choiceContainer = $("#choicesCodesPerSecond");
        refreshCodesPerSecond(false);
    } else if ( id == "choicesTransactionsPerSecond"){
        choiceContainer = $("#choicesTransactionsPerSecond");
        refreshTransactionsPerSecond(false);
    } else if ( id == "choicesTotalTPS"){
        choiceContainer = $("#choicesTotalTPS");
        refreshTotalTPS(false);
    } else if ( id == "choicesResponseTimeVsRequest"){
        choiceContainer = $("#choicesResponseTimeVsRequest");
        refreshResponseTimeVsRequest();
    } else if ( id == "choicesLatencyVsRequest"){
        choiceContainer = $("#choicesLatencyVsRequest");
        refreshLatenciesVsRequest();
    }
    var color = checked ? "black" : "#818181";
    if(choiceContainer != null) {
        choiceContainer.find("label").each(function(){
            this.style.color = color;
        });
    }
}

