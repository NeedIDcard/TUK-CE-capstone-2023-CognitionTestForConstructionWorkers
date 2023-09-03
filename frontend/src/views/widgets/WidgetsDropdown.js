import React, { useEffect, useState } from 'react'
import {
  CRow,
  CCol,
  CDropdown,
  CDropdownMenu,
  CDropdownItem,
  CDropdownToggle,
  CWidgetStatsA,
} from '@coreui/react'
import axios from 'axios'
import { getStyle } from '@coreui/utils'
import { CChartBar, CChartLine } from '@coreui/react-chartjs'
import CIcon from '@coreui/icons-react'
import { cilArrowBottom, cilArrowTop, cilOptions } from '@coreui/icons'
let a = 0
const WidgetsDropdown = () => {
  const token = sessionStorage.getItem('token')
  axios.defaults.headers.common[`Authorization`] = token
  const today = new Date()
  const [pass, setPass] = useState(0)
  const [absence, setAbsence] = useState(0)
  const [np, setNp] = useState(0)
  const [danger, setDanger] = useState(0)
  const setdata = (data) => {
    console.log(data)
    console.log(Object.keys(data).length)
    var pa = 0
    var npa = 0
    var abs = 0
    var dan = 0
    if (data != null) {
      const month = today.getMonth() + 1
      const date = today.getDate()
      console.log(`month : ${month}, date : ${date}, today : ${today}`)
      console.log(data[2].lastTestedDate)
      for (var i = 0; i < Object.keys(data).length; i++) {
        if (
          data[i].lastTestedDate != null &&
          month === data[i].lastTestedDate[1] &&
          date === data[i].lastTestedDate[2]
        ) {
          console.log(`lastTestday : ${data[i].lastTestedDate}`)
          if (data[i].risk === 'NORMAL') pa++
          else if (data[i].risk === 'LOW_RISK') dan++
          else npa++
        } else abs++
      }
    }
    setPass(pa)
    setNp(npa)
    setAbsence(abs)
    setDanger(dan)
  }
  useEffect(() => {
    axios
      .get('https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080/admin/subjects', {
        params: {
          curPageNum: 1,
          contentPerPage: 1000,
        },
        headers: {
          'Access-Control-Allow-Origin': 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080',
          withCredentials: true,
          baseURL: 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080',
        },
      })
      .then((response) => {
        //console.log(response)
        setdata(response.data.results.page.contents.subject)
      })
      .catch((error) => {
        console.error('Error data', error)
      })
  })
  return (
    <CRow>
      <CCol sm={6} lg={3}>
        <CWidgetStatsA
          className="mb-4"
          color="primary"
          value={
            <>
              {`${pass} `}
              {console.log(a)}
            </>
          }
          title="Pass"
        />
      </CCol>
      <CCol sm={6} lg={3}>
        <CWidgetStatsA
          className="mb-4"
          color="dark"
          value={
            <>
              {`${absence}`}
              {console.log(a)}
            </>
          }
          title="absence"
        />
      </CCol>
      <CCol sm={6} lg={3}>
        <CWidgetStatsA
          className="mb-4"
          color="warning"
          value={
            <>
              {`${danger}`}
              {console.log('')}
            </>
          }
          title="Danger"
        />
      </CCol>
      <CCol sm={6} lg={3}>
        <CWidgetStatsA
          className="mb-4"
          color="danger"
          value={
            <>
              {`${np}`}
              {console.log('')}
            </>
          }
          title="Non-pass"
        />
      </CCol>
    </CRow>
  )
}

export default WidgetsDropdown
