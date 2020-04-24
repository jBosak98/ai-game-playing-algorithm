import React from "react";
import Spinner from 'react-loader-spinner'

const Loader = () => 
<div className="Loader">
  <Spinner          
    type="Circles"
    color="#00BFFF"
    height={150}
    width={150}
  />
</div>

export default Loader;