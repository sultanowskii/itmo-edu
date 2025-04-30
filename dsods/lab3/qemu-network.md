# Настройка сети между двумя VM с qemu и brctl

Источники:

- [https://www.spad.uk/posts/really-simple-network-bridging-with-qemu/](https://www.spad.uk/posts/really-simple-network-bridging-with-qemu/).
- [https://futurewei-cloud.github.io/ARM-Datacenter/qemu/network-aarch64-qemu-guests/](https://futurewei-cloud.github.io/ARM-Datacenter/qemu/network-aarch64-qemu-guests/)

---

(host) One-time activities:

```bash
❯ # Allow the bridge to be used
❯ sudo echo 'allow dsods-br' >> /etc/qemu/bridge.conf
❯ # Allow non-admins to use the bridge
❯ sudo chmod +s /usr/lib/qemu/qemu-bridge-helper
```

(host) Startup:

```bash
❯ # Create a bridge called 'dsods-br'
❯ sudo brctl addbr dsods-br
❯ # IP address of the bridge
❯ sudo ip addr add 192.169.0.1/24 dev dsods-br
❯ # Bring up the bridge interface
❯ sudo ip link set dsods-br up
❯
❯ # Configure iptables/nftables to forward traffic across the bridge network
❯ sudo iptables -I FORWARD -m physdev --physdev-is-bridged -j ACCEPT
```

(host) Start guest0 with something like this:

```bash
  -net nic,model=virtio,macaddr=52:54:00:00:00:01 \
  -net bridge,br=dsods-br
```

(host) And guest1 with something like this:

```bash
  -net nic,model=virtio,macaddr=52:54:00:00:00:02 \
  -net bridge,br=dsods-br
```

(guest1) Set IP address:

```bash
> # ens4 could be different - look for the one with 52:54:00:00:00:01 MAC
> sudo ip addr add 192.169.0.10/24 dev ens4
> sudo ip link set ens4 up
```

(guest2) Set IP address:

```bash
> # ens4 could be different - look for the one with 52:54:00:00:00:02 MAC
> sudo ip addr add 192.169.0.11/24 dev ens4
> sudo ip link set ens4 up
```

(guest1) Check connection to the host (`192.169.0.1`) and guest2 (`192.169.0.11`):

```bash
> ping 192.169.0.1
> ping 192.169.0.11
```
