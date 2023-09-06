import { React, useState, useEffect } from 'react'
import { useDispatch } from 'react-redux'
import qs from 'qs'
import {
  CButton,
  CCard,
  CCardBody,
  CCol,
  CContainer,
  CForm,
  CFormInput,
  CInputGroup,
  CInputGroupText,
  CRow,
  CFormSelect,
} from '@coreui/react'
import CIcon from '@coreui/icons-react'
import { cilLockLocked, cilUser } from '@coreui/icons'
import axios from 'axios'

//var mode = 'sole'
const Register = () => {
  const dispatch = useDispatch()
  const token = sessionStorage.getItem('token')
  const [ID, setID] = useState('')
  const [Name, setName] = useState('')
  const [Password, setPassword] = useState('')
  const [Age, setAge] = useState('')
  const [Phone, setPhone] = useState('')
  const [loading, setLoading] = useState(false)
  const [Gender, setGender] = useState(null)
  const [Carrer, setCarrer] = useState('')
  const [currentPage, setCurrentPage] = useState(1)
  useEffect(() => {
    /*...*/
  }, [])
  const paginate = (pageNumber) => {
    setCurrentPage(pageNumber)
  }
  const UserFunc = (e) => {
    e.preventDefault()
    const Subjects = [
      {
        name: Name,
        username: ID,
        password: Password,
        age: Age,
        career: Carrer,
        phoneNum: Phone,
        sex: Gender,
      },
    ]
    axios.defaults.headers.common['Authorization'] = `${token}`
    axios
      .post(
        'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080/admin/subject',
        {
          mode: 'sole',
          subjects: Subjects,
        },
        {
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080',
          },
          withCredentials: true,
          baseURL: 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080',
        },
      )
      .then((response) => {
        console.log(response.data)
        window.location.href = '/#/users'
      })
      .catch((error) => {
        alert(error.message)
      })
  }
  return (
    <div className="bg-light min-vh-100 d-flex flex-row align-items-center">
      <CContainer>
        <CRow className="justify-content-center">
          <CCol md={9} lg={7} xl={6}>
            <CCard className="mx-4">
              <CCardBody className="p-4">
                <CForm onSubmit={UserFunc}>
                  <h2>Subject Create</h2>
                  <p className="text-medium-emphasis">Create subject account</p>
                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilUser} />
                    </CInputGroupText>
                    <CFormInput
                      placeholder="Username"
                      autoComplete="Username"
                      value={Name}
                      onChange={(e) => setName(e.target.value)}
                    />
                  </CInputGroup>
                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilUser} />
                    </CInputGroupText>
                    <CFormInput
                      placeholder="ID"
                      autoComplete="ID"
                      value={ID}
                      onChange={(e) => setID(e.target.value)}
                    />
                  </CInputGroup>
                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilLockLocked} />
                    </CInputGroupText>
                    <CFormInput
                      type="password"
                      placeholder="Password"
                      autoComplete="new-password"
                      value={Password}
                      onChange={(e) => setPassword(e.target.value)}
                    />
                  </CInputGroup>
                  <CInputGroup className="mb-4">
                    <CInputGroupText>
                      <CIcon icon={cilLockLocked} />
                    </CInputGroupText>
                    <CFormInput
                      type="password"
                      placeholder="Repeat password"
                      autoComplete="new-password"
                    />
                  </CInputGroup>
                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilUser} />
                    </CInputGroupText>
                    <CFormInput
                      placeholder="Age"
                      autoComplete="Age"
                      value={Age}
                      onChange={(e) => setAge(e.target.value)}
                    />
                  </CInputGroup>
                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilUser} />
                    </CInputGroupText>
                    <CFormInput
                      placeholder="Phonenumber"
                      autoComplete="Phonenumber"
                      value={Phone}
                      onChange={(e) => setPhone(e.target.value)}
                    />
                  </CInputGroup>
                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilUser} />
                    </CInputGroupText>
                    <CFormSelect
                      id="select gender"
                      value={Gender}
                      onChange={(e) => setGender(e.target.value)}
                    >
                      <option disabled selected hidden>
                        gender
                      </option>
                      <option value={0}>male</option>
                      <option value={1}>female</option>
                    </CFormSelect>
                  </CInputGroup>
                  <div className="d-grid">
                    <CButton type="submit" color="success">
                      Create Account
                    </CButton>
                  </div>
                </CForm>
              </CCardBody>
            </CCard>
            <CButton href="/#/register_sub2">+</CButton>
          </CCol>
        </CRow>
      </CContainer>
    </div>
  )
}
export default Register
