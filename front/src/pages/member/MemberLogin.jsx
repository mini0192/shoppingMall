import { useContext, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import Url from "../../config/backendConnect";
import axios from "axios";
import { NAME } from "../../config/Context";


function MemberLogin() {
    const navigate = useNavigate()

    const [contextName, setContextName] = useContext(NAME)

    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")

    const inputPassword = text => {
        setPassword(text.target.value)
    }
    
    const inputEmail = text => {
        setEmail(text.target.value)
    }

    const submitButton = () => {
        const data = new FormData()
        data.append("username", email)
        data.append("password", password)

        axios.post(`${Url}/login`, data)
        .then(res => {
            const resToken = res.headers['authorization']
            const resRefreshToken = res.headers['refresh']
            localStorage.setItem("token", resToken);
            localStorage.setItem("refreshToken", resRefreshToken);

            axios.get(`${Url}/member/authed`, {headers: {Authorization: resToken, Refresh: resRefreshToken}})
            .then(res => {
                const resName = res.data.name
                setContextName(resName)
                localStorage.setItem("name", resName);
                localStorage.setItem("username", res.data.username)
            })

            navigate("/")
        })
        .catch(err => {
            alert("아이디/비밀번호를 확인해주세요.")
        })
    }

    return (
        <section>
            <div className="form-floating mb-3">
                <input type="email" className="form-control" placeholder="name@example.com" onChange={ inputEmail }/>
                <label htmlFor="floatingInput">이메일</label>
            </div>
            <div className="form-floating">
                <input type="password" className="form-control" placeholder="Password" onChange={ inputPassword }/>
                <label htmlFor="floatingPassword">비밀번호</label>
            </div>

            <br/>
            <div className="d-grid gap-2 col-6 mx-auto">
                <button className="btn btn-info" type="button" onClick={ submitButton }>로그인</button>
            </div>

            
            <div className="container text-center paddingObj" style={{marginTop: "50px"}}>
                <div className="row">
                    <div className="col">
                        <Link to="/member/find" className="linkObj"><p>비밀번호 찾기</p></Link>
                    </div>
                    <div className="col">
                        <Link to="/member/join" className="linkObj"><p>회원가입</p></Link>
                    </div>
                </div>
            </div>
            
        </section>
    )
}

export default MemberLogin;