import axios from "axios";
import React, { useState } from "react";
import Url from "../../config/backendConnect";
import { useNavigate } from "react-router-dom";

function AddItem() {

    const navigate = useNavigate()

    const [preview, setPreview] = useState("")

    const [file, setFile] = useState();
    const [name, setName] = useState("")
    const [price, setPrice] = useState(0)
    const [content, setContent] = useState("")


    const previewFunc = (e) => {
        setFile(e.target.files)
        if (e.target.files && e.target.files[0]) {
            const file = e.target.files[0];
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = () => {
                setPreview(reader.result)
            }
        }
    }

    const inputName = text => {
        setName(text.target.value)
    }

    const inputPrice = text => {
        setPrice(text.target.value)
    }
    
    const inputContent = text => {
        setContent(text.target.value)
    }

    const submitButton = () => {
        const data = {
            name: name,
            price: price,
            content: content
        }

        const formData = new FormData();

        if(file === undefined) {
            alert("이미지를 등록해주세요")
            return
        }
        Array.from(file).forEach((e) => {
            formData.append('image', e);
        });
        
        formData.append('item', new Blob([JSON.stringify(data)], {
            type: "application/json"
        }));

        const config = {
            headers: {
                "Contest-Type": "multipart/form-data",
                Authorization: localStorage.getItem("token"),
                Refresh: localStorage.getItem("refreshToken")
            }
        };

        axios.post(`${Url}/item/admin`, formData, config)
        .then(res => {
            navigate("/")
        })
        .catch(err => {
            if(err.response.data.status === 403) {
                alert("권한이 없습니다.")
            }
        })
    }

    return (
        <section>
            <div className="row marginTopObjDouble" style={{textAlign: "center"}}>
                <div className="row">
                    <div className="col"></div>
                    <div className="col-6">
                        <h3 style={{display: "inline"}}>상품 추가</h3>
                    </div>
                    <div className="col"></div>
                </div>
            </div>
            <hr/>
            <div className="container text-center marginTopObjDouble">
                <div className="row">
                    <div className="col">
                        <div className="input-group">
                            <input onChange={ previewFunc } type="file" multiple className="form-control" accept="image/*" aria-describedby="inputGroupFileAddon04" aria-label="Upload"/>
                        </div>
                        <img src={ preview } className="img-fluid" style={{marginTop: "10px"}} alt=""/>
                    </div>
                    <div className="col">
                        <div className="form-floating mb-3">
                            <input type="text" className="form-control" placeholder="이름" onChange={ inputName }/>
                            <label htmlFor="floatingInput">이름</label>
                        </div>
                        <div className="form-floating mb-3">
                            <input type="number" className="form-control" placeholder="가격" onChange={ inputPrice }/>
                            <label htmlFor="floatingInput">가격</label>
                        </div>
                        <div className="form-floating">
                            <textarea className="form-control" placeholder="Leave a comment here" style={{height: "100px"}} onChange={ inputContent }/>
                            <label htmlFor="floatingTextarea2">설명</label>
                        </div>
                        <div className="d-grid gap-2 col-6 mx-auto marginObj">
                            <button className="btn btn-info" type="button" onClick={ submitButton }>상품등록</button>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    )
}

export default AddItem;