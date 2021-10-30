import React, { useState } from "react";

export default function HelloWorld() {
  const [showText, setShowText] = useState(false);

  return (
    <div>
      <button type="button" onClick={() => setShowText(true)}>
        Press me
      </button>
      {showText && <p>Hello world</p>}
    </div>
  );
}
