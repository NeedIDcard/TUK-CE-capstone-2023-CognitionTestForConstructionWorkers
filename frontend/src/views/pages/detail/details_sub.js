import React, { useEffect, useRef, useState } from 'react'
import { CCard, CCardBody, CCol, CCardHeader, CRow, CButton, CContainer } from '@coreui/react'
import {
  CChartBar,
  CChartDoughnut,
  CChartLine,
  CChartPie,
  CChartPolarArea,
  CChartRadar,
} from '@coreui/react-chartjs'
import { DocsCallout } from 'src/components'
import axios from 'axios'

const Details_sub = () => {
  const random = () => Math.round(Math.random() * 100)
  const token = sessionStorage.getItem('token')
  axios.defaults.headers.common['Authorization'] = `${token}`
  //let id = sessnStorage.getItem('sub_name')
  const subject_information = JSON.parse(localStorage.getItem('userinfo'))
  console.log(subject_information)
  let id = localStorage.getItem('id')
  console.log(id)
  let game1data = [],
    game2data = [],
    game3data = [],
    game4data = [],
    game5data = [],
    date = []
  const [chart1Data, setChart1Data] = useState({
    labels: [],
    datasets: [
      {
        label: 'Count of Collision',
        backgroundColor: 'rgba(151, 187, 205, 0.2)',
        borderColor: 'rgba(151, 187, 205, 1)',
        pointBackgroundColor: 'rgba(151, 187, 205, 0.5)',
        pointBorderColor: '#fff',
        data: [],
      },
      {
        label: 'Standard',
        backgroundColor: 'rgba(255, 70, 50, 0.2)',
        borderColor: 'rgba(255,70, 50, 1)',
        pointBackgroundColor: 'rgba(151, 187, 205, 1)',
        pointBorderColor: '#fff',
        data: [],
      },
    ],
  })

  const [chart2Data, setChart2Data] = useState({
    labels: [],
    datasets: [
      {
        label: 'Minimum of Response Time',
        backgroundColor: 'rgba(151, 187, 205, 0.2)',
        borderColor: 'rgba(151, 187, 205, 1)',
        pointBackgroundColor: 'rgba(151, 187, 205, 0.5)',
        pointBorderColor: '#fff',
        data: [],
      },
      {
        label: 'Standard',
        backgroundColor: 'rgba(255, 70, 50, 0.2)',
        borderColor: 'rgba(255,70, 50, 1)',
        pointBackgroundColor: 'rgba(151, 187, 205, 1)',
        pointBorderColor: '#fff',
        data: [],
      },
    ],
  })

  const [chart3Data, setChart3Data] = useState({
    labels: [],
    datasets: [
      {
        label: 'Elapsed Time',
        backgroundColor: 'rgba(151, 187, 205, 0.2)',
        borderColor: 'rgba(151, 187, 205, 1)',
        pointBackgroundColor: 'rgba(151, 187, 205, 0.5)',
        pointBorderColor: '#fff',
        data: [],
      },
      {
        label: 'Standard',
        backgroundColor: 'rgba(255, 70, 50, 0.2)',
        borderColor: 'rgba(255,70, 50, 1)',
        pointBackgroundColor: 'rgba(151, 187, 205, 1)',
        pointBorderColor: '#fff',
        data: [],
      },
    ],
  })

  const [chart4Data, setChart4Data] = useState({
    labels: [],
    datasets: [
      {
        label: 'Minimum Response Time',
        backgroundColor: 'rgba(151, 187, 205, 0.2)',
        borderColor: 'rgba(151, 187, 205, 1)',
        pointBackgroundColor: 'rgba(151, 187, 205, 0.5)',
        pointBorderColor: '#fff',
        data: [],
      },
      {
        label: 'Standard',
        backgroundColor: 'rgba(255, 70, 50, 0.2)',
        borderColor: 'rgba(255,70, 50, 1)',
        pointBackgroundColor: 'rgba(151, 187, 205, 1)',
        pointBorderColor: '#fff',
        data: [],
      },
    ],
  })
  const awsdata = useRef(null)
  useEffect(() => {
    axios
      .get(`https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080/subject/${id}/test-result`, {
        params: {
          curPageNum: 1,
          contentPerPage: 7,
        },
      })
      .then((response) => {
        //console.log(response)
        if (response != null) {
          awsdata.current = response.data.results.page.contents.testResult
          console.log(awsdata.current)
          const num = awsdata.current.length // >= 7 ? 7 : awsdata.current.length
          //console.log(JSON.stringify(awsdata.current[2]))
          //console.log(awsdata.current[0])
          for (let i = 0; i < num; i++) {
            if (awsdata.current !== null) {
              game1data.push(awsdata.current[i].mazeResult)
              game2data.push(awsdata.current[i].decisionMakingResult)
              game3data.push(awsdata.current[i].conveyorResult)
              game4data.push(awsdata.current[i].twoHandResult)
              game5data.push(awsdata.current[i].digitSpanResult)
              date.push(awsdata.current[i].date.toString())
            }
          }
        }
        const labels = date.map((item) => item)
        const values = game1data.map((item) => item.collisionCount)
        //console.log(game2data)
        //console.log(labels)
        setChart1Data((prevChart1Data) => ({
          ...prevChart1Data,
          labels,
          datasets: [
            {
              ...prevChart1Data.datasets[0],
              data: values,
            },
            prevChart1Data.datasets[1],
          ],
        }))

        const values2 = game2data.map((item) => item.minResponseTime)

        setChart2Data((prevChart2Data) => ({
          ...prevChart2Data,
          labels,
          datasets: [
            {
              ...prevChart2Data.datasets[0],
              data: values2,
            },
            prevChart2Data.datasets[1],
          ],
        }))

        const values3 = game3data.map((item) => item.elapsedTime)
        setChart3Data((prevChart3Data) => ({
          ...prevChart3Data,
          labels,
          datasets: [
            {
              ...prevChart3Data.datasets[0],
              data: values3,
            },
            prevChart3Data.datasets[1],
          ],
        }))

        const values4 = game4data.map((item) => item.minResponseTime)
        setChart4Data((prevChart4Data) => ({
          ...prevChart4Data,
          labels,
          datasets: [
            {
              ...prevChart4Data.datasets[0],
              data: values4,
            },
            prevChart4Data.datasets[1],
          ],
        }))
      })
      .catch((error) => {
        console.error('Error aws data', error)
      })
  }, [])

  //console.log(game1data)
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response1 = await axios.get('/dummy2.json')
        const Data1 = response1.data

        const labels = date.map((item) => item)
        const values = game1data.map((item) => item.responseTime)
        //console.log(values)
        //console.log(labels)
        setChart1Data((prevChart1Data) => ({
          ...prevChart1Data,
          labels,
          datasets: [
            {
              ...prevChart1Data.datasets[0],
              data: values,
            },
            prevChart1Data.datasets[1],
          ],
        }))
        //console.log(labels)
        const response2 = await axios.get('/standard.json')
        const data2 = response2.data

        const stan1value = data2[0].value
        const stan1data = Array.from({ length: 7 }, () => stan1value)
        const stan2value = data2[1].value
        const stan2data = Array.from({ length: 7 }, () => stan2value)
        const stan3value = data2[2].value
        const stan3data = Array.from({ length: 7 }, () => stan3value)
        const stan4value = data2[3].value
        const stan4data = Array.from({ length: 7 }, () => stan4value)
        //console.log(Data1)
        setChart1Data((prevChart1Data) => ({
          ...prevChart1Data,
          labels,
          datasets: [
            prevChart1Data.datasets[0],
            {
              ...prevChart1Data.datasets[1],
              data: stan1data,
            },
          ],
        }))

        const response3 = await axios.get('/dummy3.json')
        const Data3 = response3.data

        //const labels3 = Data3.map((item) => item.date)
        //const values3 = game2data.map((item) => item.mean1divRT)
        /*
        setChart2Data((prevChart2Data) => ({
          ...prevChart2Data,
          labels,
          datasets: [
            {
              ...prevChart2Data.datasets[0],
              data: values3,
            },
            prevChart2Data.datasets[1],
          ],
        }))
        */
        setChart2Data((prevChart2Data) => ({
          ...prevChart2Data,
          labels,
          datasets: [
            prevChart2Data.datasets[0],
            {
              ...prevChart2Data.datasets[1],
              data: stan2data,
            },
          ],
        }))

        const response4 = await axios.get('/dummy4.json')
        const Data4 = response4.data

        const labels4 = Data4.map((item) => item.date)
        const values4 = Data4.map((item) => item.value)

        setChart3Data((prevChart3Data) => ({
          ...prevChart3Data,
          labels4,
          datasets: [
            {
              ...prevChart3Data.datasets[0],
              data: values4,
            },
            prevChart3Data.datasets[1],
          ],
        }))

        setChart3Data((prevChart3Data) => ({
          ...prevChart3Data,
          labels,
          datasets: [
            prevChart3Data.datasets[0],
            {
              ...prevChart3Data.datasets[1],
              data: stan3data,
            },
          ],
        }))
        const response5 = await axios.get('/dummy5.json')
        const Data5 = response5.data

        const labels5 = Data5.map((item) => item.date)
        const values5 = Data5.map((item) => item.value)

        setChart4Data((prevChart4Data) => ({
          ...prevChart4Data,
          labels5,
          datasets: [
            {
              ...prevChart4Data.datasets[0],
              data: values5,
            },
            prevChart4Data.datasets[1],
          ],
        }))

        setChart4Data((prevChart4Data) => ({
          ...prevChart4Data,
          labels,
          datasets: [
            prevChart4Data.datasets[0],
            {
              ...prevChart4Data.datasets[1],
              data: stan4data,
            },
          ],
        }))
      } catch (error) {
        console.error('Error fetching data:', error)
      }
    }
    fetchData()
  }, [])

  const LogoutFunc = (e) => {
    const temp1 = token
    axios
      .post('https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080/admin/logout', null, {
        headers: {
          Authorization: '$(temp1)',
        },
      })
      .then((response) => {
        console.log(response)
        window.location.href = '/#/login_sub'
      })

    if (temp1) {
      sessionStorage.removeItem('token')
    }
  }

  return (
    <div className="bg-light min-vh-100 d-flex align-items-center">
      <CContainer>
        <CRow classname="justify-content-center">
          <CCol xs={12}>
            <CCardHeader>
              {/*
            <strong>{name}</strong>
            <p></p>
            <div style={{ display: 'flex', justifyContent: 'space-between' }}>
              <small>
                <p>
                  {`age : ${subject_information.age}`} {`carrer : ${subject_information.career}`}
                </p>
              </small>
              <CButton href="#/infor_change">information change</CButton>
            </div>
            <p></p>
    */}
            </CCardHeader>
          </CCol>
          <CCol xs={6}>
            <CCard className="mb-4">
              <CCardHeader>maze test</CCardHeader>
              <CCardBody>
                <CChartLine
                  data={
                    chart1Data /*{
                  labels: ['6/1', '6/2', '6/3', '6/4', '6/5', '6/6', '6/7'],
                  datasets: [
                    {
                      label: 'User data',
                      backgroundColor: 'rgba(151, 187, 205, 0.2)',
                      borderColor: 'rgba(151, 187, 205, 1)',
                      pointBackgroundColor: 'rgba(220, 220, 220, 1)',
                      pointBorderColor: '#fff',
                      data: [],
                    },
                    {
                      label: 'standard',
                      backgroundColor: 'rgba(255, 70, 50, 0.2)',
                      borderColor: 'rgba(255,70, 50, 1)',
                      pointBackgroundColor: 'rgba(151, 187, 205, 1)',
                      pointBorderColor: '#fff',
                      data: [],
                    },
                  ],
                }*/
                  }
                />
              </CCardBody>
            </CCard>
          </CCol>
          <CCol xs={6}>
            <CCard className="mb-4">
              <CCardHeader>decisionmaking test</CCardHeader>
              <CCardBody>
                <CChartLine data={chart2Data} />
              </CCardBody>
            </CCard>
          </CCol>
          <CCol xs={6}>
            <CCard className="mb-4">
              <CCardHeader>conveyorbelt test</CCardHeader>
              <CCardBody>
                <CChartLine data={chart3Data} />
              </CCardBody>
            </CCard>
          </CCol>
          <CCol xs={6}>
            <CCard className="mb-4">
              <CCardHeader>twohand test</CCardHeader>
              <CCardBody>
                <CChartLine data={chart4Data} />
              </CCardBody>
            </CCard>
          </CCol>
          <div>
            <CButton onClick={LogoutFunc}>logout</CButton>
          </div>
        </CRow>
      </CContainer>
    </div>
  )
}

export default Details_sub
