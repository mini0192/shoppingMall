import React, { useState } from 'react';
import axios from 'axios';

function Add() {
  const [name, setName] = useState('');
  const [price, setPrice] = useState('');
  const [files, setFiles] = useState([]);
  const [item, setItem] = useState({});

  const submit = () => {
    setItem({
      name: name,
      price: price
    });
    const formData = new FormData();
    files.map(file => {
      formData.append("previewImage", file)
    });
    formData.append("item", new Blob([JSON.stringify(item)], {type: "application/json"}))
    
    axios.post("http://localhost:8080/items", formData)
    .then(response => {
      const object = response.data;
      console.log(object);
    })
    .catch(response => {
      console.log(response);
    })
  }

  return (
    <div>
      <input type="text" name="name"
      onChange={(e) => { 
        setName(e.target.value);
        setItem({
          name: name,
          price: price
        });
      }}/><br/>

      <input type="text" name="price"
      onChange={(e) => { 
        setPrice(e.target.value);
        setItem({
          name: name,
          price: price
        });
      }}/><br/>

      <input type="file" name="imageInput" multiple accept='image/*'
      onChange={(e) => {
        console.log(e.target.files)
        setFiles(Array.from(e.target.files));
      }}/><br/>

      <input type="button" value="submit" onClick={ submit }/>
    </div>
  );
}

export default Add;