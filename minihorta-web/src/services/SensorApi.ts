import axios from "axios";

const SensorApi = axios.create({
	baseURL: "http://localhost:3000/",
	timeout: 3_000,
});

export default SensorApi;