import axios from 'axios'
import router from "@/router";

// Create a new Axios object
const request = axios.create({
    baseURL: 'http://localhost:9090',
    timeout: 30000
})

// Request interceptor
// perform some processing on the request before it is sent
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    let user = JSON.parse(localStorage.getItem("hcs-user") || '{}')
    config.headers['token'] = user.token   //Set request headers

    return config
}, error => {
    console.error('request error: ' + error) // for debug
    return Promise.reject(error)
});

// Response interceptor
// uniformly handle the results after receiving a response from an API
request.interceptors.response.use(
    response => {
        let res = response.data;

        // Compatible with string data returned by the server
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        if(res.code === '401'){
            router.push('/login')
        }
        return res;
    },
    error => {
        console.error('response error: ' + error) // for debug
        return Promise.reject(error)
    }
)


export default request