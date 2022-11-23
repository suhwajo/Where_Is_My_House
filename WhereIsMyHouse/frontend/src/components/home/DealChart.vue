<template>
  <LineChartGenerator
    :chart-options="chartOptions"
    :chart-data="chartData"
    :chart-id="chartId"
    :dataset-id-key="datasetIdKey"
    :plugins="plugins"
    :css-classes="cssClasses"
    :styles="styles"
    :width="width"
    :height="height"
  />
</template>

<script>
import { Line as LineChartGenerator } from "vue-chartjs/legacy";

import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  LineElement,
  LinearScale,
  CategoryScale,
  PointElement,
} from "chart.js";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  LineElement,
  LinearScale,
  CategoryScale,
  PointElement
);

export default {
  name: "LineChart",
  components: {
    LineChartGenerator,
  },
  props: {
    deals: Array,
    area: String,
    chartId: {
      type: String,
      default: "line-chart",
    },
    datasetIdKey: {
      type: String,
      default: "label",
    },
    width: {
      type: Number,
      default: 400,
    },
    height: {
      type: Number,
      default: 400,
    },
    cssClasses: {
      default: "",
      type: String,
    },
    styles: {
      type: Object,
      default: () => {},
    },
    plugins: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      chartData: {
        labels: [
          "January",
          "February",
          "March",
          "April",
          "May",
          "June",
          "July",
        ],
        datasets: [
          {
            label: "실거래가",
            backgroundColor: "#f87979",
            data: [],
          },
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          x: {
            grid: {
              display: false,
            },
          },
        },
        stroke: {
          curve: "smooth",
          width: 2,
        },
      },
    };
  },
  watch: {
    area: function (newVal) {
      if (newVal != "") {
        var data = { datasets: [], labels: [] };
        var dataset_aa = {
          data: [],
          label: "실거래가",
          backgroundColor: "#f87979",
        };

        for (let i = 0; i < this.deals.length; i++) {
          data.labels.push(
            this.deals[i].dealYear + "-" + this.deals[i].dealMonth
          );
          dataset_aa.data.push(this.deals[i].dealAmount.replace(",", "") * 1);
        }
        data.datasets.push(dataset_aa);
        this.chartData = data;
      }
    },
  },
};
</script>
