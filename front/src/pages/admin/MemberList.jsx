import axios from "axios";
import { useEffect, useState } from "react";
import Url from "../../config/backendConnect";
import PageNumberConfig from "../../config/PageNumberConfig";
import { useNavigate } from "react-router-dom";

function MemberList() {
    
    const DEFULTCOUNT = 12
    let pageNumber = 1;

    const navigate = useNavigate()

    const [count, setCount] = useState(DEFULTCOUNT)
    const [pageData, setPageData] = useState({})
    const [item, setItem] = useState([])

    const [status, setStatus] = useState("모두")
    const [locked, setLocked] = useState("")

    const [dropInfo, setDropInfo] = useState(DEFULTCOUNT)

    const headers = {
        Authorization: localStorage.getItem("token"),
        Refresh: localStorage.getItem("refreshToken")
    }

    const adminMember = id => {
        axios.put(`${Url}/member/admin/admin/${id}?admin=true`,{}, {headers})
        .then(res => {
            alert("권한 부여가 완료되었습니다.")
            getAPI(DEFULTCOUNT, pageNumber, locked)
        })
        .catch(err => {
            if(err.response.data.status === 403) {
                alert("권한이 없습니다.")
            }
        })
    }

    const unAdminMember = id => {
        axios.put(`${Url}/member/admin/admin/${id}?admin=false`,{}, {headers})
        .then(res => {
            alert("권한 해제가 완료되었습니다.")
            getAPI(DEFULTCOUNT, pageNumber, locked)
        })
        .catch(err => {
            if(err.response.data.status === 403) {
                alert("권한이 없습니다.")
            }
        })
    }

    const lockMember = id => {
        axios.put(`${Url}/member/admin/lock/${id}?lock=true`,{}, {headers})
        .then(res => {
            alert("잠금이 완료되었습니다.")
            getAPI(DEFULTCOUNT, pageNumber, locked)
        })
        .catch(err => {
            if(err.response.data.status === 403) {
                alert("권한이 없습니다.")
            }
        })
    }

    const unLockMember = id => {
        axios.put(`${Url}/member/admin/lock/${id}?lock=false`,{}, {headers})
        .then(res => {
            alert("잠금 해제가 완료되었습니다.")
            getAPI(DEFULTCOUNT, pageNumber, locked)
        })
        .catch(err => {
            if(err.response.data.status === 403) {
                alert("권한이 없습니다.")
            }
        })
    }

    const getAPI = (count, page, option) => {
        axios.get(`${Url}/member/admin/findAll?count=${count}&page=${page}&locked=${option}`, {headers})
        .then(res => {
            pageNumber = res.data.pageable?.pageNumber + 1;
            setPageData(res.data)
            setItem(res.data.content)
        })
        .catch(err => {
            if(err.response.data.status === 403) {
                alert("권한이 없습니다.")
            }
        })
    }

    useEffect(() => {
        getAPI(DEFULTCOUNT, pageNumber, locked)
    },[])

    function dropdownFunc(number) {
        return (
            <button className="dropdown-item" onClick={ () => { getAPI(number, pageNumber, locked); setDropInfo(number); setCount(number);} }>{number}개씩 보기</button>
        )
    }

    function lockedFunc(data) {
        if(data === "true") {
            return(<p>잠김</p>)
        }
    }

    function lock(data) {
        if(data.locked === "false") {
            return(<button type="button" className="btn btn-outline-danger" onClick={() => lockMember(data.id)}>잠금</button>)
        }
        return(<button type="button" className="btn btn-outline-danger" onClick={() => unLockMember(data.id)}>잠금 해제</button>)
    }

    function admin(data) {
        if(data.role.indexOf("ROLE_ADMIN") === -1) {
            return(<button type="button" className="btn btn-outline-info" onClick={() => adminMember(data.id)}>권한 부여</button>)
        }
        return(<button type="button" className="btn btn-outline-info" onClick={() => unAdminMember(data.id)}>권한 해제</button>)
    }

    return (
        <section>
            <div className="row marginTopObjDouble" style={{textAlign: "center"}}>
                <div className="row">
                    <div className="col"></div>
                    <div className="col-6">
                        <h3 style={{display: "inline"}}>유저 목록</h3>
                    </div>
                    <div className="col">
                        <div className="btn-group marginObj" style={{display: "inline", textAlign: "right"}}>
                            <button type="button" className="btn btn-info dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                { `${dropInfo}개씩 보기` }
                            </button>
                            <ul className="dropdown-menu">
                                { dropdownFunc(DEFULTCOUNT) }
                                { dropdownFunc(DEFULTCOUNT * 2) }
                                { dropdownFunc(DEFULTCOUNT * 3) }
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <hr/>
            <div className="marginTopObj" style={{textAlign: "center"}}>
                <table className="table">
                    <thead>
                        <tr>
                        <th scope="col"><h6>ID</h6></th>
                        <th scope="col"><h6>이메일</h6></th>
                        <th scope="col"><h6>이름</h6></th>
                        <th scope="col"><h6>권한</h6></th>
                        <th scope="col">
                            <div className="btn-group">
                                <button className="btn btn-outline-secondary btn-sm dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    {status}
                                </button>
                                <ul className="dropdown-menu">
                                    <button className="dropdown-item" onClick={ () => { setStatus("모두"); setLocked(""); getAPI(count, 1, "");} }>모두</button>
                                    <button className="dropdown-item" onClick={ () => { setStatus("접속 가능"); setLocked("false"); getAPI(count, 1, "false");} }>접속 가능</button>
                                    <button className="dropdown-item" onClick={ () => { setStatus("잠김"); setLocked("true"); getAPI(count, 1, "true");} }>잠김</button>
                                </ul>
                            </div>
                        </th>
                        <th scope="col"><h6>관리</h6></th>
                        </tr>
                    </thead>
                    <tbody>
                    { item.map(data => (
                        <tr key={ data.id }>
                            <th scope="row"><p>{data.id}</p></th>
                            <th scope="row"><p>{data.username}</p></th>
                            <td><p>{data.name}</p></td>
                            <td>
                                <p>
                                    {
                                        data.role.map( r => (
                                            r.split("_")[1]+" "
                                        ))
                                    }
                                </p>
                            </td>
                            <td><p>{lockedFunc(data.locked)}</p></td>
                            <td>
                                { admin(data) }
                                { lock(data) }
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                { PageNumberConfig(pageData, count, locked, getAPI) }
            </div>
        </section>
    )
}

export default MemberList