import React, { useState, useEffect } from 'react'
import {
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CRow,
  CTable,
  CTableBody,
  CTableDataCell,
  CTableHead,
  CTableHeaderCell,
  CTableRow,
  CTableFoot,
  CButton,
} from '@coreui/react'
import { DocsExample } from 'src/components'
import axios from 'axios'
import Cite_Dropdown from './cite_dropdown/cite_dropdown_sub'

const Tables2 = () => {
  const [test, settest] = useState([])
  const [data, setData] = useState([])
  const [data2, setData2] = useState([])
  const [currentPage, setCurrentPage] = useState(1)
  const [itemsPerPage] = useState(10)
  const token = sessionStorage.getItem('token')
  const today = new Date()
  const today2 = [today.getFullYear(), today.getMonth() + 1, today.getDate()]
  console.log(today2)
  axios.defaults.headers.common['Authorization'] = `${token}`
  const subjectname = sessionStorage.getItem('subjectname')
  if (subjectname) {
    console.log(subjectname)
    sessionStorage.removeItem('subjectname')
    console.log('subjectName removed from session')
  } else {
    console.log('subjectName not found in session')
  }
  const credit = sessionStorage.getItem('credit')
  if (credit !== 'admin') {
    window.alert('admin 계정으로 접속해야 합니다.')
    sessionStorage.clear()
    window.location.href = '#/login_sub'
  }
  useEffect(() => {
    axios
      .get('/dummy.json')
      .then((response) => {
        setData(response.data)
      })
      .catch((error) => {
        console.error('Error fetching data:', error)
      })
  }, [])
  //testestestest
  useEffect(() => {
    axios
      .get('https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080/admin/subjects', {
        params: {
          curPageNum: 1,
          contentPerPage: 10,
        },
        headers: {
          'Access-Control-Allow-Origin': 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080',
          withCredentials: true,
          baseURL: 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080',
        },
      })
      .then((response) => {
        setData2(response.data.results.page.contents.subject)
        console.log(response)
      })
      .catch((error) => {
        console.error('Error aws data:', error)
      })
  }, [])

  const indexOfLastItem = currentPage * itemsPerPage
  const indexOfFirstItem = indexOfLastItem - itemsPerPage
  const currentItems = data.slice(indexOfFirstItem, indexOfLastItem)
  const currentItems2 = data2.slice(indexOfFirstItem, indexOfLastItem)
  const paginate = (pageNumber) => {
    setCurrentPage(pageNumber)
  }

  const saveSubjectName = (item) => {
    sessionStorage.setItem('sub_name', item.name)
    sessionStorage.setItem('sub_id', item.id)
    localStorage.setItem('userinfo', JSON.stringify(item))
  }
  return (
    <>
      <CRow>
        <CCol xs={12}>
          <CCard className="mb-4">
            <CCardHeader>
              <strong>Subject Table</strong> <small></small>
            </CCardHeader>
            <CCardBody>
              <CTable>
                <CTableHead>
                  <CTableRow>
                    <CTableHeaderCell scope="col">#</CTableHeaderCell>
                    <CTableHeaderCell scope="col">Name</CTableHeaderCell>
                    <CTableHeaderCell scope="col">Age</CTableHeaderCell>
                    <CTableHeaderCell scope="col">Field</CTableHeaderCell>
                    <CTableHeaderCell scope="col">Detail Work</CTableHeaderCell>
                    <CTableHeaderCell scope="col">Career</CTableHeaderCell>
                  </CTableRow>
                </CTableHead>
                <CTableBody>
                  {currentItems2.map((item, index) => (
                    <CTableRow
                      scope="col"
                      key={item.num}
                      style={{
                        backgroundColor:
                          item.lastTestedDate !== null &&
                          item.lastTestedDate[1] === today2[1] &&
                          item.lastTestedDate[1] === today2[1] &&
                          item.lastTestedDate[2] === today2[2]
                            ? item.risk === 'HIGH_RISK'
                              ? 'red'
                              : item.risk === 'LOW_RISK'
                              ? 'lightyellow'
                              : item.risk === 'MIDDLE_RISK'
                              ? 'yellow'
                              : 'white'
                            : 'grey',
                      }}
                    >
                      <CTableDataCell>{indexOfFirstItem + index + 1}</CTableDataCell>
                      <CTableDataCell>
                        <a href="#/details" onClick={() => saveSubjectName(item)}>
                          {item.name}
                        </a>
                      </CTableDataCell>
                      <CTableDataCell>{item.age}</CTableDataCell>
                      <CTableDataCell>
                        <Cite_Dropdown
                          sitename={item.field.name}
                          userid={item.id}
                          username={item.name}
                        />
                      </CTableDataCell>
                      <CTableDataCell>{item.detailedJob}</CTableDataCell>
                      <CTableDataCell>{item.career}</CTableDataCell>
                    </CTableRow>
                  ))}
                </CTableBody>
              </CTable>
              <div>
                {data.length > itemsPerPage && (
                  <nav>
                    <ul className="pagination">
                      {Array(Math.ceil(data2.length / itemsPerPage))
                        .fill()
                        .map((_, index) => (
                          <li
                            key={index}
                            className={`page-item ${currentPage === index + 1 ? 'active' : ''}`}
                          >
                            <button className="page-link" onClick={() => paginate(index + 1)}>
                              {index + 1}
                            </button>
                          </li>
                        ))}
                    </ul>
                    <CButton href="#/register_sub">new subject</CButton>
                  </nav>
                )}
              </div>
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>
    </>
  )
}

export default Tables2
