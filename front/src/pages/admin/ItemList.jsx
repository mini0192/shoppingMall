import axios from "axios";
import { useEffect, useState } from "react";
import ReplaceMouny from "../../config/ReplaceMouny";
import Url from "../../config/backendConnect";
import PageNumberConfig from "../../config/PageNumberConfig";

function ItemList() {
    
    const DEFULTCOUNT = 12
    let pageNumber = 1

    const [count, setCount] = useState(DEFULTCOUNT)
    const [item, setItem] = useState([])
    const [pageData, setPageData] = useState({})

    const [dropInfo, setDropInfo] = useState(DEFULTCOUNT)

    const [deleted, setDeleted] = useState("")
    const [status, setStatus] = useState("모두")

    const headers = {
        Authorization: localStorage.getItem("token"),
        Refresh: localStorage.getItem("refreshToken")
    }

    const deleteItem = id => {
        axios.delete(`${Url}/item/admin/${id}?deleted=true`, {headers})
        .then(res => {
            alert("삭제가 완료되었습니다.")
            getAPI(DEFULTCOUNT, pageNumber, deleted)
        })
        .catch(err => {
            if(err.response.data.status === 403) {
                alert("권한이 없습니다.")
            }
        })
    }

    const unDeleteItem = id => {
        axios.delete(`${Url}/item/admin/${id}?deleted=false`, {headers})
        .then(res => {
            alert("복구가 완료되었습니다.")
            getAPI(DEFULTCOUNT, pageNumber, deleted)
        })
        .catch(err => {
            if(err.response.data.status === 403) {
                alert("권한이 없습니다.")
            }
        })
    }



    const getAPI = (count, page, deleted) => {
        axios.get(`${Url}/item/admin?count=${count}&page=${page}&deleted=${deleted}`, {headers})
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
        getAPI(DEFULTCOUNT, pageNumber, "")
    },[])

    
    function deletedFunc(data) {
        if(data === "true") {
            return(<p>삭제됨</p>)
        }
    }

    function deletedBtnFunc(data) {
        if(data.deleted === "true") {
            return(<button type="button" className="btn btn-danger" onClick={() => unDeleteItem(data.id)}>복구</button>)
        }
        return(<button type="button" className="btn btn-outline-danger" onClick={() => deleteItem(data.id)}>삭제</button>)
    }

    function dropdownFunc(number) {
        return (
            <button className="dropdown-item" onClick={ () => { getAPI(number, pageNumber, deleted); setDropInfo(number); setCount(number);} }>{number}개씩 보기</button>
        )
    }

    return (
        <section>
            <div className="row marginTopObjDouble" style={{textAlign: "center"}}>
                <div className="row">
                    <div className="col"></div>
                    <div className="col-6">
                        <h3 style={{display: "inline"}}>제품 목록</h3>
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
                        <th scope="col"><h6>이름</h6></th>
                        <th scope="col"><h6>가격</h6></th>
                        <th scope="col"><h6>분류</h6></th>
                        <th scope="col">
                            <div className="btn-group">
                                <button className="btn btn-outline-secondary btn-sm dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    {status}
                                </button>
                                <ul className="dropdown-menu">
                                    <button className="dropdown-item" onClick={ () => { setStatus("모두"); setDeleted(""); getAPI(count, 1, "");} }>모두</button>
                                    <button className="dropdown-item" onClick={ () => { setStatus("판매 중"); setDeleted("false"); getAPI(count, 1, "false");} }>판매 중</button>
                                    <button className="dropdown-item" onClick={ () => { setStatus("삭제됨"); setDeleted("true"); getAPI(count, 1, "true");} }>삭제됨</button>
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
                            <td><p>{data.name}</p></td>
                            <td><p>{ReplaceMouny(data.price)}</p></td>
                            <td><p>{data.type}</p></td>
                            <td>
                                {deletedFunc(data.deleted)}
                            </td>
                            <td>
                                {deletedBtnFunc(data)}
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                { PageNumberConfig(pageData, count, deleted, getAPI) }
            </div>
        </section>
    )
}

export default ItemList