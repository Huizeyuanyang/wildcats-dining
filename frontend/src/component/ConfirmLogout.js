function ConfirmLogout(props) {
  return (
    <>
      <div className="dialog-overlay" onClick={props.onCancel}></div>
      <div className="dialog">
        <h3>{props.title}</h3>
        <p>{props.message}</p>
        <div className="dialog-buttons">
          <button className="dialog-cancel" onClick={props.onCancel}>
            Cancel
          </button>
          <button className="dialog-confirm" onClick={props.onConfirm}>
            {props.confirmText}
          </button>
        </div>
      </div>
    </>
  );
}

export default ConfirmLogout;