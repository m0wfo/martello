require 'socket'
require 'ipaddr'
MULTICAST_ADDR = "238.0.0.123"
PORT = 1337
ip =  IPAddr.new(MULTICAST_ADDR).hton + IPAddr.new("0.0.0.0").hton
sock = UDPSocket.new
sock.setsockopt(Socket::IPPROTO_IP, Socket::IP_ADD_MEMBERSHIP, ip)
sock.setsockopt(Socket::SOL_SOCKET,Socket::SO_REUSEADDR, true)
sock.bind(Socket::INADDR_ANY, PORT)
loop do
  msg, info = sock.recvfrom(1500)
  puts "MSG: #{msg} from #{info[2]} (#{info[3]})/#{info[1]} len #{msg.size}"
end
