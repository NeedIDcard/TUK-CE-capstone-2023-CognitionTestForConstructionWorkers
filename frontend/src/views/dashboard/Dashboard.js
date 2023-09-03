import { React, useEffect, useState } from 'react'
import axios from 'axios'
import {
  CAvatar,
  CButton,
  CButtonGroup,
  CCard,
  CCardBody,
  CCardFooter,
  CCardHeader,
  CCol,
  CProgress,
  CRow,
  CTable,
  CTableBody,
  CTableDataCell,
  CTableHead,
  CTableHeaderCell,
  CTableRow,
} from '@coreui/react'
import { CChartLine } from '@coreui/react-chartjs'
import { getStyle, hexToRgba } from '@coreui/utils'
import CIcon from '@coreui/icons-react'
import {
  cibCcAmex,
  cibCcApplePay,
  cibCcMastercard,
  cibCcPaypal,
  cibCcStripe,
  cibCcVisa,
  cibGoogle,
  cibFacebook,
  cibLinkedin,
  cifBr,
  cifEs,
  cifFr,
  cifIn,
  cifPl,
  cifUs,
  cibTwitter,
  cilCloudDownload,
  cilPeople,
  cilUser,
  cilUserFemale,
} from '@coreui/icons'

import avatar1 from 'src/assets/images/avatars/1.jpg'
import avatar2 from 'src/assets/images/avatars/2.jpg'
import avatar3 from 'src/assets/images/avatars/3.jpg'
import avatar4 from 'src/assets/images/avatars/4.jpg'
import avatar5 from 'src/assets/images/avatars/5.jpg'
import avatar6 from 'src/assets/images/avatars/6.jpg'

import WidgetsBrand from '../widgets/WidgetsBrand'
import WidgetsDropdown from '../widgets/WidgetsDropdown'

const Dashboard = () => {
  if (sessionStorage.getItem('token') === null) window.location.href = '/#/login_sub'
  const token = sessionStorage.getItem('token')
  let np_data = [],
    date = []
  axios.defaults.headers.common['Authorization'] = `${token}`
  const random = (min, max) => Math.floor(Math.random() * (max - min + 1) + min)
  const credit = sessionStorage.getItem('credit')
  if (credit !== 'admin') {
    window.alert('admin 계정으로 접속해야 합니다.')
    sessionStorage.clear()
    window.location.href = '#/login_sub'
  }
  const [npchartData, setNpchartDate] = useState({
    labels: [],
    datasets: [
      {
        label: 'My First dataset',
        backgroundColor: hexToRgba(getStyle('--cui-info'), 10),
        borderColor: getStyle('--cui-danger'),
        pointHoverBackgroundColor: getStyle('--cui-info'),
        borderWidth: 2,
        data: [],
        fill: true,
      },
    ],
  })
  useEffect(() => {
    axios
      .get('https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080/admin/np_count', {
        params: {
          curPageNum: 1,
          contentPerPage: 7,
        },
        headers: {
          'Access-Control-Allow-Origin': 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080',
          withCredentials: true,
          baseURL: 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080',
        },
      })
      .then((response) => {
        console.log(response)
        const awsdata = response.data.results.NotPassedCount
        const num = awsdata.length
        for (let i = 0; i < num; i++) {
          if (awsdata !== null) {
            np_data.push(awsdata[i].count)
            date.push(awsdata[i].date.toString())
          }
        }
        console.log(np_data)
        console.log(date)
        const labels = date.map((item) => item)
        //console.log(`labels : ${labels}`)
        const np_data2 = np_data.map((item) => item)
        //console.log(`data : ${np_data2}`)
        setNpchartDate((prevData) => ({
          ...prevData,
          labels,
          datasets: [
            {
              ...prevData.datasets,
              data: np_data2,
            },
          ],
        }))
      })
      .catch((error) => {
        console.error('Error data', error)
      })
  }, [])

  /*
  
  /*
  class Today extends React.Component {
    render() {
      const today = new Date()
      const option = { day: '2-digit', month: '2-digit', year: '2-digit' }
      const date = today.toLocaleDateString(undefined, option)
      console.log(date)
      return date
    }
    
  }*/
  return (
    <>
      <WidgetsDropdown />
      <CCard className="mb-4">
        <CCardBody>
          <CRow>
            <CCol sm={5}>
              <h4 id="traffic" className="card-title mb-0">
                Non Pass
              </h4>
              <div className="small text-medium-emphasis"> 2023/08/24 </div>
            </CCol>
            {/*
            <CCol sm={7} className="d-none d-md-block">
              <CButton color="primary" className="float-end">
                <CIcon icon={cilCloudDownload} />
              </CButton>
              <CButtonGroup className="float-end me-3">
                {['Day', 'Month', 'Year'].map((value) => (
                  <CButton
                    color="outline-secondary"
                    key={value}
                    className="mx-0"
                    active={value === 'Month'}
                  >
                    {value}
                  </CButton>
                ))}
              </CButtonGroup>
            </CCol>
                */}
          </CRow>
          <CChartLine
            style={{ height: '300px', marginTop: '40px' }}
            data={
              npchartData
              /*
              {
                labels: [`2023,8,24`, `2023,8,25`],
                datasets: [
                  {
                    label: 'My First dataset',
                    backgroundColor: hexToRgba(getStyle('--cui-info'), 10),
                    borderColor: getStyle('--cui-danger'),
                    pointHoverBackgroundColor: getStyle('--cui-info'),
                    borderWidth: 2,
                    data: [3, 3],
                    fill: true,
                  },
                  {
                    label: 'My Second dataset',
                    backgroundColor: 'transparent',
                    borderColor: getStyle('--cui-success'),
                    pointHoverBackgroundColor: getStyle('--cui-success'),
                    borderWidth: 2,
                    data: [
                      random(50, 200),
                      random(50, 200),
                      random(50, 200),
                      random(50, 200),
                      random(50, 200),
                      random(50, 200),
                      random(50, 200),
                    ],
                  },
                  {
                    label: 'My Third dataset',
                    backgroundColor: 'transparent',
                    borderColor: getStyle('--cui-danger'),
                    pointHoverBackgroundColor: getStyle('--cui-danger'),
                    borderWidth: 1,
                    borderDash: [8, 5],
                    data: [65, 65, 65, 65, 65, 65, 65],
                  },
                ],
              }
              */
            }
            options={{
              maintainAspectRatio: false,
              plugins: {
                legend: {
                  display: false,
                },
              },
              scales: {
                x: {
                  grid: {
                    drawOnChartArea: false,
                  },
                },
                y: {
                  ticks: {
                    beginAtZero: true,
                    maxTicksLimit: 5,
                    stepSize: Math.ceil(250 / 5),
                    max: 250,
                  },
                },
              },
              elements: {
                line: {
                  tension: 0.4,
                },
                point: {
                  radius: 0,
                  hitRadius: 10,
                  hoverRadius: 4,
                  hoverBorderWidth: 3,
                },
              },
            }}
          />
        </CCardBody>
      </CCard>
    </>
  )
}

export default Dashboard
