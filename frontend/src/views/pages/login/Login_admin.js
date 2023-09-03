import React, { useEffect, useState } from 'react'
import qs from 'qs'
import { Link } from 'react-router-dom'
import {
  CButton,
  CCard,
  CCardBody,
  CCardGroup,
  CCol,
  CContainer,
  CForm,
  CFormInput,
  CInputGroup,
  CInputGroupText,
  CRow,
  //CInputGroupCheckbox,
} from '@coreui/react'
import CIcon from '@coreui/icons-react'
import { cilLockLocked, cilUser } from '@coreui/icons'
import { useDispatch } from 'react-redux'
import axios from 'axios'
import { Cookies } from 'react-cookie'

//const cookies = new Cookies()

const Login = () => {
  const dispatch = useDispatch()

  const [ID, setID] = useState('')
  const [Password, setPassword] = useState('')
  const [su, setsu] = useState(false)
  const [loading, setLoading] = useState(false)
  const [msg, setMsg] = useState('')

  useEffect(() => {
    /*...*/
  }, [msg])

  const LoginFunc = (e) => {
    let link = 'abc'
    let credit = ' '
    if (su === true) {
      link = 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080/super/admin/login'
      credit = 'super'
    } else {
      link = 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080/admin/login'
      credit = 'admin'
    }
    e.preventDefault()
    const data = {
      username: ID,
      password: Password,
    }
    axios
      .post(link, qs.stringify(data), {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
          'Access-Control-Allow-Origin': 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080',
        },
        withCredentials: true,
        baseURL: 'https://oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080',
      })
      .then((response) => {
        //const responseCookies = response
        //console.log(responseCookies)
        console.log(response)
        const token = response.data.results.token
        sessionStorage.setItem('token', token)
        sessionStorage.setItem('credit', credit)
        if (su === false) {
          window.location.href = '/#/dashboard'
        } else {
          window.location.href = '/#/admins'
        }
      })
      .catch((error) => {
        alert(error.message)
      })
  }
  return (
    <div className="bg-light min-vh-100 d-flex flex-row align-items-center">
      <CContainer>
        <CRow className="justify-content-center">
          <CCol md={8}>
            <CCardGroup>
              <CCard className="p-4">
                <CCardBody>
                  <CForm onSubmit={LoginFunc}>
                    <h1>Login</h1>
                    <p className="text-medium-emphasis">Sign In to your account</p>
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
                    <CInputGroup className="mb-4">
                      <CInputGroupText>
                        <CIcon icon={cilLockLocked} />
                      </CInputGroupText>
                      <CFormInput
                        type="password"
                        placeholder="Password"
                        autoComplete="current-password"
                        value={Password}
                        onChange={(e) => setPassword(e.target.value)}
                      />
                    </CInputGroup>
                    <CRow>
                      <CCol xs={6}>
                        <CButton type="submit" color="primary" className="px-4">
                          Login
                        </CButton>
                        <div style={{ display: 'inline', marginLeft: '10px' }}>
                          <input
                            type="checkbox"
                            id="suadmin"
                            checked={su}
                            onChange={() => setsu(!su)}
                            label="suadmin login"
                          />
                          <label htmlFor="su">suadmin login</label>
                        </div>
                        {msg && <div>{msg}</div>}
                      </CCol>
                    </CRow>
                  </CForm>
                </CCardBody>
              </CCard>
            </CCardGroup>
          </CCol>
        </CRow>
      </CContainer>
    </div>
  )
}

export default Login
