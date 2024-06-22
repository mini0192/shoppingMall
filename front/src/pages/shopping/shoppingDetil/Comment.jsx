import { useParams } from "react-router-dom";
import Url from "../../../config/backendConnect";
import axios from "axios";
import { useEffect, useState } from "react";
import PageNumberConfig from "../../../config/PageNumberConfig";

function Comment() {
    const [comment, setComment] = useState("")
    const [commentList, setCommentList] = useState([])

    const [pageData, setPageData] = useState({})

    const {id} = useParams();

    const sendAPI = (count, page, option) => {
        axios.get(`${Url}/item/comment/${id}?page=${page}`)
        .then(res => {
            setCommentList(res.data.content)
            setPageData(res.data)
        })
        .catch(err => {
            console.log(err.response.data.error)
        })
    }
    useEffect(() => {
        sendAPI(5, 1)
    },[])

    const inputComment = text => {
        setComment(text.target.value)
    }

    const submitButton = () => {
        const data = {
            review: comment
        }

        const headers = {
            Authorization: localStorage.getItem("token"),
            Refresh: localStorage.getItem("refreshToken")
        };

        axios.post(`${Url}/item/comment/authed/${id}`, data, { headers })
        .then(res => {
            sendAPI("", "")
        })
        .catch(err => {
            alert(err.response.data.error[0])
        })
    }

    return (
        <div style={{textAlign: "center"}}>
            <section style={{margin: "100px"}}>
                <ul className="list-group">
                    {
                        commentList?.map(e => (
                            <li className="list-group-item" style={{textAlign: "left"}} key={e.id}>
                                <h6>{ e.name }</h6>
                                <p>{ e.review }</p>
                            </li>
                        ))
                    }
                </ul>
                { PageNumberConfig(pageData, 5, "", sendAPI) }
            </section>

            <section style={{margin: "100px"}}>
                <div className="form-floating">
                    <textarea className="form-control" placeholder="Leave a comment here" style={{height: "100px"}} onChange={ inputComment }/>
                    <label htmlFor="floatingTextarea2">Comments</label>
                </div>
                <button className="btn btn-info" type="button" style={{marginTop: "10px"}} onClick={ submitButton }>댓글 달기</button>
            </section>
        </div>
    )
}

export default Comment;