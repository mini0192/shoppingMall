import axios from "axios";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

function ReadId() {
    const p = useParams();
    const id = p.id;

    const navigate = useNavigate();

    const [item, setItem] = useState([])
    const [image, setImage] = useState([])
    const [comments, setComments] = useState([])

    const [name, setName] = useState('');
    const [text, setText] = useState('');
    const [comment, setComment] = useState({
        "name": "",
        "review": ""
    });


    const reviewGet = () => {
        axios.get("http://localhost:8080/items/comments/"+id)
        .then(response => {
            const data = response.data;
            setComments(data);
            console.log(data);
        });
    }

    const review = () => {
        setComment({
            name: name,
            review: text
        })

        const formData = new FormData();
        formData.append("comment", new Blob([JSON.stringify(comment)], {type: "application/json"}))
        axios.post("http://localhost:8080/items/comments/"+id, formData)
        .then(response => {
            reviewGet();
        })
        .catch(err => {
            alert("입력 값이 잘못되었습니다.")
            console.log(err.response.data)
        })
    }

    useEffect(() => {
        axios.get("http://localhost:8080/items/"+id)
        .then(response => {
            const data = response.data
            if(data == '') return;
            setItem(data);
            setImage(data.previewImage);
        });
        reviewGet();
    }, []);

    function deleteId() {
        axios.delete("http://localhost:8080/items/"+id)
        .then(response => {
            alert("삭제가 완료되었습니다.");
            navigate("/read");
        })
        .catch(error => {
            console.log(error.response.data);
        })
    }

    return (
        <div>
            <div key = { item.id } style={{
                    padding: "30px 100px 0 100px",
                    display: "grid",
                    gridTemplateColumns: "2fr 1fr",
                    marginBottom: "100px"
                }}>
                <div key = { image } style={{ gridColumn: "1/2" }}>
                    { image.map(image => (
                        <img src={ "http://localhost:8080/" + image } width='100%'/>
                    ))}
                </div>
                <div style={{ gridColumn: "2/3",
                                textAlign: "left",
                                paddingLeft: "50px"
                }}>
                    <h1> { item.name } </h1>
                    <p> { item.price+"원"} </p><br/>
                    <input type="button" value="수정" onClick={ () => { navigate(`/put/${ id }`) } }/>
                <input type="button" value="삭제" onClick={ deleteId } style={{display: "inline"}}/>
                </div>
            </div>

            <h3>REVIEW</h3>
            <hr/>
            <div style={{
                    padding: "0 100px 0 100px",
                    display: "grid",
                    gridTemplateColumns: "1fr 2fr 70px"
                }}>
                <input type="text" placeholder="Name" style={{ gridColumn: "1/2", margin: "20px" }}
                onChange={(e) => { 
                    setName(e.target.value);
                    setComment({
                        name: name,
                        review: text
                    })
                  }}/>

                <input type="text" placeholder="Comment" style={{ gridColumn: "2/3", margin: "20px 20px 20px 0"}}
                onChange={(e) => { 
                    setText(e.target.value);
                    setComment({
                        name: name,
                        review: text
                    })
                  }}/>

                <input type="button" value="submit" onClick={ review } style={{ gridColumn: "3/4", margin: "20px 0 20px 0"}}/>
            </div>
                {comments.map(com => (
                    <div key = { com.id } style={{
                            padding: "0 100px 0 100px",
                            display: "grid",
                            gridTemplateColumns: "1fr 2fr 100px"
                        }}>
                        <p style={{ gridColumn: "1/2",
                                    margin: "20px",
                                    textAlign: "left"
                                    }}><strong>{ com.name }</strong></p>
                        <p style={{ gridColumn: "2/3",
                                    margin: "20px 20px 20px 0",
                                    textAlign: "left"
                                    }}>{ com.review }</p>
                        <div style={{ gridColumn: "3/4", margin: "20px 0 20px 0",}}>
                            <input type="button" value="수정" />
                            <input type="button" value="삭제" onClick={ () => {
                                axios.delete("http://localhost:8080/items/comments/"+com.id)
                                .then(response => {
                                    alert("삭제가 완료되었습니다.")
                                    reviewGet();
                                });
                            }}/>
                        </div>
                    </div>
                ))}
        </div>
    )
}

export default ReadId;