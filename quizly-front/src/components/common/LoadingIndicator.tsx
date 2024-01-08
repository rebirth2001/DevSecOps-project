import { Loading3QuartersOutlined } from "@ant-design/icons";
import { Spin } from "antd";

export default function LoadingIndicator() {
  const antIcon = (
    <Loading3QuartersOutlined style={{ fontSize: 30 }} spin={true} />
  );
  return (
    <Spin
      indicator={antIcon}
      style={{ display: "block", textAlign: "center", marginTop: 30 }}
    />
  );
}
