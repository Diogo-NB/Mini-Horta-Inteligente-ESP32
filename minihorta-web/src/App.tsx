import "./App.css";
import { defaults } from "chart.js/auto";
import { Line } from "react-chartjs-2";

import { SensorType } from "./types/SensorType";
import { useEffect, useState } from "react";
import SensorApi from "./services/SensorApi";

defaults.maintainAspectRatio = false;
defaults.responsive = true;

defaults.plugins.title.display = true;
defaults.plugins.title.align = "start";
defaults.plugins.title.font.size = 28;
defaults.plugins.title.color = "black";

const App: React.FC = () => {
  const [polling, setPolling] = useState<number>(5);
  const [sensors, setSensors] = useState<SensorType[]>([]);
  // {
  //   timestamp: "2025-10-25",
  //   humidity: 15,
  //   temperature: 30
  // },
  // {
  //   timestamp: "2025-10-26",
  //   humidity: 11,
  //   temperature: 28
  // },
  // {
  //   timestamp: "2025-10-27",
  //   humidity: 13,
  //   temperature: 26
  // },
  // {
  //   timestamp: "2025-10-28",
  //   humidity: 13,
  //   temperature: 28
  // },
  // {
  //   timestamp: "2025-10-29",
  //   humidity: 15,
  //   temperature: 31
  // },
  // ]);

  useEffect(() => {
    setTimeout(() => {
      if (polling <= 0) {
        setPolling(5);
        SensorApi
          .get("/sensors")
          .then(response => {
            const { data } = response;
            console.table(data);
            setSensors(data)
          });
      } else {
        setPolling(prev => prev - 1);
      }
    }, 1_000);
  }, [polling]);

  return (
    <div className="App">
      <h2>
        Atualização: {polling} (s)
      </h2>
      <div className="dataCard">

        <Line
          data={{
            labels: sensors.map(data => data.timestamp),
            datasets: [
              {
                label: "Umidade",
                data: sensors.map(data => data.humidity),
                backgroundColor: "#064FF0",
                borderColor: "#064FF0",
              },
              {
                label: "Temperatura",
                data: sensors.map(data => data.temperature),
                backgroundColor: "#FF3030",
                borderColor: "#FF3030",
              }
            ],
          }}
          options={{
            elements: {
              line: {
                tension: 0.2,
              },
            },
            plugins: {
              title: {
                text: "Sensores ESP32",
              },
            },
          }}
        />
      </div>
    </div>
  );
};

export default App;