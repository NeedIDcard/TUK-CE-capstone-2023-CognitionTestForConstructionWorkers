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
import Cite_Dropdown from './cite_dropdown/cite_dropdown'

const Tables_Admin = () => {
  const [test, settest] = useState([])
  const [data, setData] = useState([])
  const [data2, setData2] = useState([])
  const [currentPage, setCurrentPage] = useState(1)
  const [itemsPerPage] = useState(10)
  const token = sessionStorage.getItem('token')
  axios.defaults.headers.common['Authorization'] = `${token}`
  const subjectname = sessionStorage.getItem('subjectname')
  const credit = sessionStorage.getItem('credit')
  if (credit !== 'super') {
    window.alert('suadmin 계정으로 접속해야 합니다.')
    sessionStorage.clear()
    window.location.href = '#/login_sub'
  }
  if (subjectname) {
    console.log(subjectname)
    sessionStorage.removeItem('subjectname')
    console.log('subjectName removed from session')
  } else {
    console.log('subjectName not found in session')
  }

  useEffect(() => {
    axios
      .get('https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080/site')
      .then((response) => {
        //setData(response.data)
        //console.log(response.data.results.fields)
      })
      .catch((error) => {
        console.error('Error fetching data:', error)
      })
  }, [])
  //testestestest
  useEffect(() => {
    axios
      .get('https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080/super/admins', {
        params: {
          curPageNum: 1,
          contentsPerPage: 10,
        },
      })
      .then((response) => {
        setData2(response.data.results.page.contents.admin)
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
  }

  const DeleteFunc = (e) => {
    axios
      .delete(`https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080/super/admin/${e}`, {
        headers: {
          'Access-Control-Allow-Origin': 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080',
        },
        withCredentials: true,
        baseURL: 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080',
      })
      .then((response) => {
        window.location.reload()
      })
      .catch((error) => {
        alert(error.message)
      })
  }

  return (
    <>
      <CRow>
        <CCol xs={12}>
          <CCard className="mb-4">
            <CCardHeader>
              <strong>Admin Table</strong> <small></small>
            </CCardHeader>
            <CCardBody>
              <CTable>
                <CTableHead>
                  <CTableRow>
                    <CTableHeaderCell scope="col">#</CTableHeaderCell>
                    <CTableHeaderCell scope="col">Name</CTableHeaderCell>
                    <CTableHeaderCell scope="col">position</CTableHeaderCell>
                    <CTableHeaderCell scope="col">Field</CTableHeaderCell>
                    <CTableHeaderCell scope="col"></CTableHeaderCell>
                  </CTableRow>
                </CTableHead>
                <CTableBody>
                  {currentItems2.map((item, index) => (
                    <CTableRow
                      scope="col"
                      key={item.num}
                      style={{
                        backgroundColor:
                          item.risk === 'HIGH_RISK'
                            ? 'red'
                            : item.risk === 'LOW_RISK'
                            ? 'lightyellow'
                            : item.risk === 'MIDDLE_RISK'
                            ? 'yellow'
                            : 'white',
                      }}
                    >
                      <CTableDataCell>{indexOfFirstItem + index + 1}</CTableDataCell>
                      <CTableDataCell>
                        <a>{item.name}</a>
                      </CTableDataCell>
                      <CTableDataCell>{item.position}</CTableDataCell>
                      <CTableDataCell>
                        <Cite_Dropdown
                          sitename={item.field.name}
                          userid={item.i}
                          username={item.name}
                        />
                      </CTableDataCell>
                      <CTableDataCell
                        style={{ backgroundColor: 'red' }}
                        onClick={() => DeleteFunc(item.id)}
                      >
                        Delete
                      </CTableDataCell>
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
                  </nav>
                )}
              </div>
              <CButton href="#/register_ad">new admin</CButton>
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>
    </>
  )
}

export default Tables_Admin
